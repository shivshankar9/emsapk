package com.bigdatanyze.ems1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.databinding.ActivityMainBinding;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;
import com.bumptech.glide.Glide; // Add Glide import

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private BusinessProfileViewModel viewModel;
	private static final int EDIT_PROFILE_REQUEST_CODE = 1; // Request code for edit profile

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check if user is logged in
		SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
		boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

		if (!isLoggedIn) {
			// If not logged in, redirect to LoginActivity
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish(); // Finish MainActivity so the user cannot return to it
			return; // Exit onCreate
		}

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Initialize ViewModel
		viewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Observe changes to the business profile
		viewModel.getBusinessProfile().observe(this, profile -> {
			if (profile != null) {
				binding.tvHeading.setText(profile.getBusinessName()); // Update the business name

				String logoUri = profile.getLogoUri();
				Log.d("LogoUri", "Logo URI: " + logoUri);
				if (logoUri != null && !logoUri.isEmpty()) {
					// Use Glide to load the logo
					Glide.with(this)
							.load(Uri.parse(logoUri))
							.placeholder(R.drawable.ic_logo) // Placeholder while loading
							.error(R.drawable.ic_logo) // Default logo on error
							.into(binding.logoImageView); // Target ImageView
				} else {
					binding.logoImageView.setImageResource(R.drawable.ic_logo); // Set a default logo if null or empty
				}
			}
		});

		setupBottomNavigation();
		setupHeaderIcons();  // Setup click listeners for icons and name/logo
	}

	private void setupBottomNavigation() {
		binding.bottomNavigationView.setOnItemSelectedListener(item -> {
			Fragment selectedFragment = null;
			int itemId = item.getItemId();

			// Determine which fragment to load
			if (itemId == R.id.nav_home) {
				selectedFragment = new HomeFragment();
			} else if (itemId == R.id.nav_dashboard) {
				selectedFragment = new DashboardFragment();
			} else if (itemId == R.id.nav_menu) {
				selectedFragment = new MenuFragment();
			}

			// Replace the fragment if one is selected
			if (selectedFragment != null) {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, selectedFragment)
						.commit();
			}

			return true;
		});

		// Set default selection and load the default fragment
		binding.bottomNavigationView.setSelectedItemId(R.id.nav_home);
	}

	private void setupHeaderIcons() {
		// Settings icon click listener
		binding.ivSettings.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(intent);  // Redirect to SettingsActivity
		});

		// Notifications icon click listener
		binding.ivNotifications.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
			startActivity(intent);  // Redirect to NotificationsActivity
		});

		// Profile icon click listener
		binding.ivProfile.setOnClickListener(v -> openEditBusinessProfile());

		// Logo click listener
		binding.logoImageView.setOnClickListener(v -> openEditBusinessProfile());

		// Name click listener
		binding.tvHeading.setOnClickListener(v -> openEditBusinessProfile());
	}

	private void openEditBusinessProfile() {
		Log.d("ProfileClick", "Edit Business Profile clicked");
		Intent intent = new Intent(MainActivity.this, EditBusinessProfileActivity.class);
		startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE); // Start for result
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
			// Assuming the updated profile is sent back in the Intent
			BusinessProfile updatedProfile = (BusinessProfile) data.getSerializableExtra("updatedProfile");
			if (updatedProfile != null) {
				binding.tvHeading.setText(updatedProfile.getBusinessName()); // Update the business name

				String logoUri = updatedProfile.getLogoUri();
				Log.d("LogoUri", "Updated Logo URI: " + logoUri);
				if (logoUri != null && !logoUri.isEmpty()) {
					Glide.with(this)
							.load(Uri.parse(logoUri))
							.placeholder(R.drawable.ic_logo) // Placeholder while loading
							.error(R.drawable.ic_logo) // Default logo on error
							.into(binding.logoImageView); // Target ImageView
				} else {
					binding.logoImageView.setImageResource(R.drawable.ic_logo); // Set a default logo if null or empty
				}
			}
		}
	}
}
