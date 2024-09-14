package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.SearchView;
import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
import com.bigdatanyze.ems1.adapter.ExpenseAdapter;
import com.bigdatanyze.ems1.databinding.FragmentHomeBinding;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;
import com.bigdatanyze.ems1.viewmodel.ExpenseViewModel;

public class HomeFragment extends Fragment {

	private FragmentHomeBinding binding;
	private ExpenseAdapter expenseAdapter;
	private EmployeeAdapter employeeAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Set up RecyclerViews
		binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.recyclerViewEmployee.setLayoutManager(new LinearLayoutManager(getContext()));

		// Initialize adapters
		expenseAdapter = new ExpenseAdapter();
		employeeAdapter = new EmployeeAdapter();

		// Set adapters to RecyclerViews
		binding.recyclerViewExpense.setAdapter(expenseAdapter);
		binding.recyclerViewEmployee.setAdapter(employeeAdapter);

		// Set up ViewModels
		ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
		EmployeeViewModel employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

		// Observe Expense and Employee data
		expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
			expenseAdapter.setExpenseList(expenses);
		});

		employeeViewModel.getAllEmployees().observe(getViewLifecycleOwner(), employees -> {
			employeeAdapter.setEmployeeList(employees);
		});

		// Set up SearchView to work for both Expense and Employee RecyclerViews
		binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				// No need to handle submission separately, as filtering occurs in real-time
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (binding.recyclerViewExpense.getVisibility() == View.VISIBLE) {
					expenseAdapter.filter(newText); // Filter Expense list based on the query
				} else if (binding.recyclerViewEmployee.getVisibility() == View.VISIBLE) {
					employeeAdapter.filter(newText); // Filter Employee list based on the query
				}
				return true;
			}
		});

		// Set up button click listeners to toggle between Expense and Employee views
		binding.buttonExpenseView.setOnClickListener(v -> {
			binding.recyclerViewExpense.setVisibility(View.VISIBLE);
			binding.recyclerViewEmployee.setVisibility(View.GONE);
			binding.searchView.setQueryHint("Search Expenses"); // Set SearchView hint to "Search Expenses"
		});

		binding.buttonEmployeeView.setOnClickListener(v -> {
			binding.recyclerViewExpense.setVisibility(View.GONE);
			binding.recyclerViewEmployee.setVisibility(View.VISIBLE);
			binding.searchView.setQueryHint("Search Employees"); // Set SearchView hint to "Search Employees"
		});

		// Default view to display Expenses first
		binding.recyclerViewExpense.setVisibility(View.VISIBLE);
		binding.recyclerViewEmployee.setVisibility(View.GONE);
		binding.searchView.setQueryHint("Search Expenses"); // Default placeholder for Expenses

		return view;
	}
}
