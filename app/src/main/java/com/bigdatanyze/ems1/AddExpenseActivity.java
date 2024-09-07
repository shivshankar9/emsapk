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

	private ExpenseViewModel expenseViewModel;
	private EditText editTextName;
	private EditText editTextPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);

		editTextName = findViewById(R.id.edit_text_name);
		editTextPosition = findViewById(R.id.edit_text_position);
		Button buttonSave = findViewById(R.id.button_save);

		// Initialize the ViewModel
		expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

		buttonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = editTextName.getText().toString().trim();
				String position = editTextPosition.getText().toString().trim();

				// Validate input fields
				if (!name.isEmpty() && !position.isEmpty()) {
					// Create new expense with default constructor and set properties
					Expense newExpense = new Expense();
					newExpense.setName(name);
					newExpense.setPosition(position);
					expenseViewModel.insert(newExpense);
					Toast.makeText(AddExpenseActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
					finish(); // Close activity after saving
				} else {
					// Show error message if fields are empty
					Toast.makeText(AddExpenseActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
