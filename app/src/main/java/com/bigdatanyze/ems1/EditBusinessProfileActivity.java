package com.bigdatanyze.ems1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.databinding.ActivityEditBusinessProfileBinding;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;

public class EditBusinessProfileActivity extends AppCompatActivity {

	private ActivityEditBusinessProfileBinding binding;
	private BusinessProfileViewModel viewModel;
	private Uri logoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityEditBusinessProfileBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		viewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Observe the existing profile
		viewModel.getBusinessProfile().observe(this, profile -> {
			if (profile != null) {
				binding.etBusinessName.setText(profile.getBusinessName());
				binding.ivLogo.setImageURI(Uri.parse(profile.getLogoUri()));
				logoUri = Uri.parse(profile.getLogoUri());
			}
		});

		// Logo click listener to choose a new image
		binding.ivLogo.setOnClickListener(v -> {
			// Open an intent to choose an image
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, 1); // Request code 1 for image picking
		});

		// Save button listener
		binding.btnSave.setOnClickListener(v -> {
			String businessName = binding.etBusinessName.getText().toString();
			String logoPath = logoUri != null ? logoUri.toString() : "";

			BusinessProfile newProfile = new BusinessProfile(businessName, logoPath);
			viewModel.update(newProfile);

			// Send updated profile back to MainActivity
			Intent resultIntent = new Intent();
			resultIntent.putExtra("updatedProfile", newProfile);
			setResult(RESULT_OK, resultIntent);
			finish(); // Close activity after saving
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
			logoUri = data.getData(); // Get the image URI
			binding.ivLogo.setImageURI(logoUri); // Display the selected image
		}
	}
}
