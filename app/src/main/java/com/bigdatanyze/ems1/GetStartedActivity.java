package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GetStartedActivity extends AppCompatActivity {

	private Button buttonGetStarted;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_started);

		buttonGetStarted = findViewById(R.id.buttonGetStarted);

		buttonGetStarted.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Redirect to LoginActivity
				Intent intent = new Intent(GetStartedActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
