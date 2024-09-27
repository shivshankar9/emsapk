package com.bigdatanyze.ems1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bigdatanyze.ems1.databinding.ActivityProfileBinding;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

	private ActivityProfileBinding binding;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private Uri logoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityProfileBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
		editor = preferences.edit();

		// Load current business name and logo
		loadBusinessProfile();

		// Save button click listener
		binding.btnSaveProfile.setOnClickListener(v -> saveProfile());

		// Select logo button click listener
		binding.btnSelectLogo.setOnClickListener(v -> selectLogo());
	}

	private void loadBusinessProfile() {
		String businessName = preferences.getString("businessName", "Finverge");
		String logoUriString = preferences.getString("logoUri", null);

		binding.etBusinessName.setText(businessName);

		if (logoUriString != null) {
			logoUri = Uri.parse(logoUriString);
			binding.ivBusinessLogo.setImageURI(logoUri);
		}
	}

	private void saveProfile() {
		String businessName = binding.etBusinessName.getText().toString().trim();

		if (businessName.isEmpty()) {
			Toast.makeText(this, "Please enter a business name", Toast.LENGTH_SHORT).show();
			return;
		}

		editor.putString("businessName", businessName);

		if (logoUri != null) {
			editor.putString("logoUri", logoUri.toString());
		}

		editor.apply();
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
