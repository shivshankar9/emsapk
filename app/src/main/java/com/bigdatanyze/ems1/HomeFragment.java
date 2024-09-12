package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
import com.bigdatanyze.ems1.adapter.ExpenseAdapter;
import com.bigdatanyze.ems1.databinding.FragmentHomeBinding;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;
import com.bigdatanyze.ems1.viewmodel.ExpenseViewModel;

public class HomeFragment extends Fragment {

	private FragmentHomeBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Set up RecyclerViews
		binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.recyclerViewEmployee.setLayoutManager(new LinearLayoutManager(getContext()));

		ExpenseAdapter expenseAdapter = new ExpenseAdapter();
		EmployeeAdapter employeeAdapter = new EmployeeAdapter();

		binding.recyclerViewExpense.setAdapter(expenseAdapter);
		binding.recyclerViewEmployee.setAdapter(employeeAdapter);

		// Set up ViewModels
		ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
		EmployeeViewModel employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

		expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
			expenseAdapter.setExpenseList(expenses);
		});

		employeeViewModel.getAllEmployees().observe(getViewLifecycleOwner(), employees -> {
			employeeAdapter.setEmployeeList(employees);
		});

		// Set up button click listeners
		binding.buttonExpenseView.setOnClickListener(v -> {
			binding.recyclerViewExpense.setVisibility(View.VISIBLE);
			binding.recyclerViewEmployee.setVisibility(View.GONE);
		});

		binding.buttonEmployeeView.setOnClickListener(v -> {
			binding.recyclerViewExpense.setVisibility(View.GONE);
			binding.recyclerViewEmployee.setVisibility(View.VISIBLE);
		});

		// Default view to display (e.g., expenses by default)
		binding.recyclerViewExpense.setVisibility(View.VISIBLE);
		binding.recyclerViewEmployee.setVisibility(View.GONE);

		return view;
	}
}
