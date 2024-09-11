package com.bigdatanyze.ems1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

	private EditText editTextUsername;
	private EditText editTextPassword;
	private Button buttonLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editTextUsername = findViewById(R.id.editTextUsername);
		editTextPassword = findViewById(R.id.editTextPassword);
		buttonLogin = findViewById(R.id.buttonLogin);

		buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Validate username and password (This is a basic example)
				String username = editTextUsername.getText().toString().trim();
				String password = editTextPassword.getText().toString().trim();

				if (username.equals("admin") && password.equals("password")) {
					// Save login state
					SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean("isLoggedIn", true);
					editor.apply();

					// Redirect to MainActivity
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					// Show error message
					editTextUsername.setError("Invalid login details");
				}
			}
		});
	}
}
