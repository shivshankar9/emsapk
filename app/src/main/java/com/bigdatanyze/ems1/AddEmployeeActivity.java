package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

		employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);


		buttonSave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String name = editTextName.getText().toString().trim();
						String position = editTextPosition.getText().toString().trim();

						if (!name.isEmpty() && !position.isEmpty()) {
							Employee newEmployee = new Employee(name, position);
							employeeViewModel.insert(newEmployee);
							finish(); // Close activity after saving
						} else {
							// Optionally, you can show a toast or error message if fields are empty
						}
					}
				});
	}
}

