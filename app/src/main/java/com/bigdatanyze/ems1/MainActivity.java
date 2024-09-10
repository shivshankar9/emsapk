package com.bigdatanyze.ems1;

import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
import com.bigdatanyze.ems1.adapter.ExpenseAdapter;
import com.bigdatanyze.ems1.databinding.ActivityMainBinding;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.model.Expense;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;
import com.bigdatanyze.ems1.viewmodel.ExpenseViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding binding;
	private EmployeeViewModel employeeViewModel;
	private EmployeeAdapter employeeAdapter;
	private ExpenseViewModel expenseViewModel;
	private ExpenseAdapter expenseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check if user is logged in or first-time user
		SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
		boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

		if (!isLoggedIn) {
			// Check if it's the first time the app is launched
			boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

			if (isFirstTime) {
				// Mark that the user has launched the app
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("isFirstTime", false);
				editor.apply();

				// Redirect to GetStartedActivity
				Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
				startActivity(intent);
				finish();
				return;
			} else {
				// Redirect to LoginActivity
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				return;
			}
		}

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Employee RecyclerView setup
		binding.recyclerViewEmployee.setLayoutManager(new LinearLayoutManager(this));
		employeeAdapter = new EmployeeAdapter();
		binding.recyclerViewEmployee.setAdapter(employeeAdapter);

		// Expense RecyclerView setup
		binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(this));
		expenseAdapter = new ExpenseAdapter();
		binding.recyclerViewExpense.setAdapter(expenseAdapter);

		// Employee ViewModel
		EmployeeViewModel.Factory employeeFactory = new EmployeeViewModel.Factory(getApplication());
		employeeViewModel = new ViewModelProvider(this, employeeFactory).get(EmployeeViewModel.class);

		employeeViewModel.getAllEmployees().observe(this, new Observer<List<Employee>>() {
			@Override
			public void onChanged(List<Employee> employees) {
				employeeAdapter.setEmployeeList(employees);
			}
		});

		// Expense ViewModel
		ExpenseViewModel.Factory expenseFactory = new ExpenseViewModel.Factory(getApplication());
		expenseViewModel = new ViewModelProvider(this, expenseFactory).get(ExpenseViewModel.class);

		expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
			@Override
			public void onChanged(List<Expense> expenses) {
				expenseAdapter.setExpenseList(expenses);
			}
		});

		// Add Employee Button
		binding.buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
				startActivity(intent);
			}
		});

		// Add Expense Button
		binding.buttonAddExpense.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
				startActivity(intent);
			}
		});
	}
}
