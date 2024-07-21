package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
import com.bigdatanyze.ems1.databinding.ActivityMainBinding;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding binding;
	private EmployeeViewModel employeeViewModel;
	private EmployeeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EmployeeAdapter();
		binding.recyclerView.setAdapter(adapter);

		// Use the factory to create an instance of EmployeeViewModel
		EmployeeViewModel.Factory factory = new EmployeeViewModel.Factory(getApplication());
		employeeViewModel = new ViewModelProvider(this, factory).get(EmployeeViewModel.class);

		employeeViewModel.getAllEmployees().observe(this, new Observer<List<Employee>>() {
			@Override
			public void onChanged(List<Employee> employees) {
				adapter.setEmployeeList(employees);
			}
		});

		binding.buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
				startActivity(intent);
			}
		});
	}
}
