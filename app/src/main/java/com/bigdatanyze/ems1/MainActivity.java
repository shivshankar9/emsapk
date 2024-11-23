package com.bigdatanyze.ems1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bigdatanyze.ems1.MenuActivity.DashboardFragment;
//import com.bigdatanyze.ems1.MenuActivity.DashbordFragment;
import com.bigdatanyze.ems1.MenuActivity.HomeFragment;
import com.bigdatanyze.ems1.MenuActivity.MenuFragment;
import com.bigdatanyze.ems1.databinding.ActivityMainBinding;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private BusinessProfileViewModel viewModel;

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

		// Initialize ViewModel
		viewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		setupBottomNavigation();
	}

	private void setupBottomNavigation() {
		binding.bottomNavigationView.setOnItemSelectedListener(item -> {
			Fragment selectedFragment = null;

			if (item.getItemId() == R.id.nav_home) {
				selectedFragment = new HomeFragment();
				hideToolbar(); // Hide toolbar for HomeFragment
			} else if (item.getItemId() == R.id.nav_dashboard) {
				selectedFragment = new DashboardFragment();
				hideToolbar();// Show toolbar for DashboardFragment
			} else if (item.getItemId() == R.id.nav_menu) {
				selectedFragment = new MenuFragment();
				hideToolbar();// Show toolbar for MenuFragment
			}

			if (selectedFragment != null) {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, selectedFragment)
						.commit();
			}

			return true;
		});

		// Default to Home Fragment
		binding.bottomNavigationView.setSelectedItemId(R.id.nav_home);
	}

	public void hideToolbar() {
		binding.toolbar.setVisibility(View.GONE);
	}

	public void showToolbar() {
		binding.toolbar.setVisibility(View.VISIBLE);
	}
}
