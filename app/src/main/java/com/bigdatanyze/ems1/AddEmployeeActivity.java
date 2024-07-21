package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;

public class AddEmployeeActivity extends AppCompatActivity {

	private EmployeeViewModel employeeViewModel;
	private EditText editTextName;
	private EditText editTextPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_employee);

		editTextName = findViewById(R.id.edit_text_name);
		editTextPosition = findViewById(R.id.edit_text_position);
		Button buttonSave = findViewById(R.id.button_save);

		// Initialize the ViewModel
		employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

		buttonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = editTextName.getText().toString().trim();
				String position = editTextPosition.getText().toString().trim();

				// Validate input fields
				if (!name.isEmpty() && !position.isEmpty()) {
					// Create new employee with default constructor and set properties
					Employee newEmployee = new Employee();
					newEmployee.setName(name);
					newEmployee.setPosition(position);
					employeeViewModel.insert(newEmployee);
					Toast.makeText(AddEmployeeActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
					finish(); // Close activity after saving
				} else {
					// Show error message if fields are empty
					Toast.makeText(AddEmployeeActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
