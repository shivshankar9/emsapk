package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bigdatanyze.ems1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setupBottomNavigation();
		setupHeaderIcons();  // Setup click listeners for icons
	}

	private void setupBottomNavigation() {
		binding.bottomNavigationView.setOnItemSelectedListener(item -> {
			Fragment selectedFragment = null;
			int itemId = item.getItemId();

			if (itemId == R.id.nav_home) {
				selectedFragment = new HomeFragment();
			} else if (itemId == R.id.nav_dashboard) {
				selectedFragment = new DashboardFragment();
			} else if (itemId == R.id.nav_menu) {
				selectedFragment = new MenuFragment();
			}

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

		// Profile icon click listener (if needed)
		binding.ivProfile.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
			startActivity(intent);  // Redirect to ProfileActivity
		});
	}
}
