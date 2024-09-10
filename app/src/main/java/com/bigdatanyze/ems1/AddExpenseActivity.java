package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.Expense;
import com.bigdatanyze.ems1.viewmodel.ExpenseViewModel;

public class AddExpenseActivity extends AppCompatActivity {

	private EditText editTextAmount, editTextCategory, editTextDate, editTextDescription;
	private Button addButton;
	private ExpenseViewModel expenseViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);

		// Initialize EditTexts and Button
		editTextAmount = findViewById(R.id.editTextAmount);
		editTextCategory = findViewById(R.id.editTextCategory);
		editTextDate = findViewById(R.id.editTextDate);
		editTextDescription = findViewById(R.id.editTextDescription);
		addButton = findViewById(R.id.add_button);

		// Initialize the ViewModel
		expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

		// Set OnClickListener to the button to handle save operation
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String amount = editTextAmount.getText().toString().trim();
				String category = editTextCategory.getText().toString().trim();
				String date = editTextDate.getText().toString().trim();
				String description = editTextDescription.getText().toString().trim();

				// Validate input fields
				if (!amount.isEmpty() && !category.isEmpty() && !date.isEmpty() && !description.isEmpty()) {
					// Create new expense object and set properties
					Expense newExpense = new Expense(amount, category, date, description);

					// Insert the new expense using ViewModel
					expenseViewModel.insert(newExpense);

					// Show success message
					Toast.makeText(AddExpenseActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();

					// Close the activity after saving
					finish();
				} else {
					// Show error message if fields are empty
					Toast.makeText(AddExpenseActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
