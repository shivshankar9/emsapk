package com.bigdatanyze.ems1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditBusinessProfileActivity extends AppCompatActivity {
	private static final int PICK_IMAGE_REQUEST = 1;
	private BusinessProfileViewModel viewModel;
	private TextInputEditText etBusinessName, etCompanyAddress, etPhoneNumber, etEmail;
	private MaterialButton btnSave;
	private ImageView ivLogo;
	private Uri logoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_business_profile);

		etBusinessName = findViewById(R.id.et_business_name);
		etCompanyAddress = findViewById(R.id.et_company_address);
		etPhoneNumber = findViewById(R.id.et_phone_number);
		etEmail = findViewById(R.id.et_email);
		btnSave = findViewById(R.id.btn_save);
		ivLogo = findViewById(R.id.iv_logo);

		// Initialize ViewModel
		viewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Observe the business profile data
		viewModel.getBusinessProfile().observe(this, businessProfile -> {
			if (businessProfile != null) {
				etBusinessName.setText(businessProfile.getBusinessName());
				etCompanyAddress.setText(businessProfile.getCompanyAddress());
				etPhoneNumber.setText(businessProfile.getPhoneNumber());
				etEmail.setText(businessProfile.getEmail());

				// Set logo URI if available
				if (businessProfile.getLogoUri() != null) {
					logoUri = Uri.parse(businessProfile.getLogoUri());
					ivLogo.setImageURI(logoUri);
				}
			}
		});

		// Open image picker when clicking on logo
		ivLogo.setOnClickListener(v -> openImageChooser());

		// Handle the save button click
		btnSave.setOnClickListener(v -> {
			String businessName = etBusinessName.getText().toString().trim();
			String companyAddress = etCompanyAddress.getText().toString().trim();
			String phoneNumber = etPhoneNumber.getText().toString().trim();
			String email = etEmail.getText().toString().trim();

			if (!businessName.isEmpty()) {
				BusinessProfile updatedProfile = new BusinessProfile();
				updatedProfile.setBusinessName(businessName);
				updatedProfile.setCompanyAddress(companyAddress);
				updatedProfile.setPhoneNumber(phoneNumber);
				updatedProfile.setEmail(email);
				updatedProfile.setLogoUri(logoUri != null ? logoUri.toString() : null); // Save logo URI if selected

				// Update the ViewModel with the new profile details
				viewModel.updateBusinessProfile(updatedProfile);

				// Send the updated profile back as a result
				Intent intent = new Intent();
				intent.putExtra("updatedProfile", updatedProfile);
				setResult(RESULT_OK, intent);
				finish();
			} else {
				// Show error if the business name is empty
				etBusinessName.setError("Business name cannot be empty!");
				etBusinessName.requestFocus();
			}
		});
	}

	// Opens the image chooser
	private void openImageChooser() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_IMAGE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			logoUri = data.getData();
			ivLogo.setImageURI(logoUri); // Set the selected image as logo
		} else {
			Toast.makeText(this, "Image selection failed", Toast.LENGTH_SHORT).show();
		}
	}
}