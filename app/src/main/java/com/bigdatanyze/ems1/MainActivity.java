package com.bigdatanyze.ems1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bigdatanyze.ems1.databinding.ActivityMainBinding;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private BusinessProfileViewModel viewModel;
	private static final int STORAGE_PERMISSION_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check if user is logged in
		SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
		boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

		if (!isLoggedIn) {
			// Redirect to LoginActivity if not logged in
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			return;
		}

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Check and request storage permissions
		checkStoragePermission();

		// Initialize ViewModel
		viewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Observe changes to the business profile
		viewModel.getBusinessProfile().observe(this, profile -> {
			if (profile != null) {
				binding.tvHeading.setText(profile.getBusinessName());
				loadImage(profile.getLogoUri());
			}
		});

		setupBottomNavigation();
		setupHeaderIcons();
	}

	private void setupBottomNavigation() {
		binding.bottomNavigationView.setOnItemSelectedListener(item -> {
			Fragment selectedFragment = null;

			if (item.getItemId() == R.id.nav_home) {
				selectedFragment = new HomeFragment();
			} else if (item.getItemId() == R.id.nav_dashboard) {
				selectedFragment = new DashboardFragment();
			} else if (item.getItemId() == R.id.nav_menu) {
				selectedFragment = new MenuFragment();
			}

			if (selectedFragment != null) {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, selectedFragment)
						.commit();
			}

			return true;
		});

		binding.bottomNavigationView.setSelectedItemId(R.id.nav_home);
	}

	private void setupHeaderIcons() {
		binding.ivSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
		binding.ivNotifications.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NotificationsActivity.class)));
		binding.ivProfile.setOnClickListener(v -> openEditBusinessProfile());
		binding.logoImageView.setOnClickListener(v -> openEditBusinessProfile());
		binding.tvHeading.setOnClickListener(v -> openEditBusinessProfile());
	}

	private void openEditBusinessProfile() {
		Intent intent = new Intent(MainActivity.this, EditBusinessProfileActivity.class);
		profileEditLauncher.launch(intent);
	}

	private final ActivityResultLauncher<Intent> profileEditLauncher =
			registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
				if (result.getResultCode() == RESULT_OK && result.getData() != null) {
					BusinessProfile updatedProfile = (BusinessProfile) result.getData().getSerializableExtra("updatedProfile");
					if (updatedProfile != null) {
						binding.tvHeading.setText(updatedProfile.getBusinessName());
						loadImage(updatedProfile.getLogoUri());
					}
				}
			});

	private void loadImage(String logoUri) {
		if (logoUri != null && !logoUri.isEmpty()) {
			Glide.with(this)
					.load(Uri.parse(logoUri))
					.placeholder(R.drawable.ic_logo)
					.error(R.drawable.ic_logo)
					.into(binding.logoImageView);
		} else {
			binding.logoImageView.setImageResource(R.drawable.ic_logo);
		}
	}

	private void checkStoragePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// Android 11 and above: Check if Manage External Storage is granted
			if (!Environment.isExternalStorageManager()) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				startActivity(intent);
			}
		} else {
			// Below Android 11: Request READ_EXTERNAL_STORAGE permission
			if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this,
						new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == STORAGE_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Log.d("Permission", "Storage Permission Granted");
			} else {
				Log.d("Permission", "Storage Permission Denied");
			}
		}
	}
}
