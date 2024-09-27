package com.bigdatanyze.ems1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
	private Button buttonLogin;
	private Button buttonLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);  // Link to the settings layout

		buttonLogin = findViewById(R.id.buttonLogin);
		buttonLogout = findViewById(R.id.buttonLogout);

		// Check if user is already logged in
		SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
		boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

		// Set logout button visibility based on login state
		buttonLogout.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
		buttonLogin.setVisibility(isLoggedIn ? View.GONE : View.VISIBLE);

		buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});

		buttonLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Logout logic
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("isLoggedIn", false);
				editor.apply();

				// Redirect to LoginActivity after logout
				Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
				startActivity(intent);
				finish(); // Close SettingsActivity
			}
		});
	}
}
