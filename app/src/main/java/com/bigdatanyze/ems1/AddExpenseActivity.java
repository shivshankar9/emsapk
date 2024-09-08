package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

	private Button addButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense); // Make sure this matches your XML layout file name

		// Initialize the button with the correct ID from your layout XML
		addButton = findViewById(R.id.add_button);

		// Check if the button is properly initialized to avoid null pointer exceptions
		if (addButton != null) {
			addButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Add your logic for button click here
					Log.d("AddExpenseActivity", "Add button clicked");
					// Perform action such as saving the expense data
				}
			});
		} else {
			Log.e("AddExpenseActivity", "Button is null! Check your layout XML.");
		}
	}
}
