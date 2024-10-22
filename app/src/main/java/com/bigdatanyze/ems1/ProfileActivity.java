package com.bigdatanyze.ems1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.databinding.ActivityProfileBinding;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

	private ActivityProfileBinding binding;
	private BusinessProfileViewModel businessProfileViewModel;
	private Uri logoUri;
	private BusinessProfile businessProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityProfileBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Initialize ViewModel for database interaction
		businessProfileViewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Load current business profile
		loadBusinessProfile();

		// Save button click listener
		binding.btnSaveProfile.setOnClickListener(v -> saveProfile());

		// Select logo button click listener
		binding.btnSelectLogo.setOnClickListener(v -> selectLogo());
	}

	private void loadBusinessProfile() {
		// Observe the business profile from the database
		businessProfileViewModel.getBusinessProfile().observe(this, profile -> {
			if (profile != null) {
				businessProfile = profile;
				binding.etBusinessName.setText(profile.getBusinessName());

				if (profile.getLogoUri() != null) {
					logoUri = Uri.parse(profile.getLogoUri());
					binding.ivBusinessLogo.setImageURI(logoUri);
				}
			}
		});
	}

	private void saveProfile() {
		String businessName = binding.etBusinessName.getText().toString().trim();

		if (businessName.isEmpty()) {
			Toast.makeText(this, "Please enter a business name", Toast.LENGTH_SHORT).show();
			return;
		}

		// Update business profile object
		if (businessProfile == null) {
			businessProfile = new BusinessProfile();
		}
		businessProfile.setBusinessName(businessName);
		if (logoUri != null) {
			businessProfile.setLogoUri(logoUri.toString());
		}

		// Save the business profile to the database using ViewModel
		businessProfileViewModel.updateBusinessProfile(businessProfile);

		Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

		// Return to MainActivity
		startActivity(new Intent(ProfileActivity.this, MainActivity.class));
		finish();
	}

	private void selectLogo() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		logoPickerLauncher.launch(intent);
	}

	private final ActivityResultLauncher<Intent> logoPickerLauncher = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == RESULT_OK) {
					Intent data = result.getData();
					if (data != null) {
						logoUri = data.getData();
						try {
							Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), logoUri);
							binding.ivBusinessLogo.setImageBitmap(bitmap);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
}
