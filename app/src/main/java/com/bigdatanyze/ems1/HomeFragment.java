package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		setupRecyclerViews();
		setupViewModels();
		setupSearchView();
		setupViewToggleButtons();
		setupFloatingActionButton();

		// Default view to display Expenses first
		showExpenseView();

		return view;
	}

	private void setupRecyclerViews() {
		binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.recyclerViewEmployee.setLayoutManager(new LinearLayoutManager(getContext()));

		// Initialize adapters
		expenseAdapter = new ExpenseAdapter();
		employeeAdapter = new EmployeeAdapter();

		// Set adapters to RecyclerViews
		binding.recyclerViewExpense.setAdapter(expenseAdapter);
		binding.recyclerViewEmployee.setAdapter(employeeAdapter);
	}

	private void setupViewModels() {
		ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
		EmployeeViewModel employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

		// Observe Expense and Employee data
		expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
			expenseAdapter.setExpenseList(expenses);
		});

		employeeViewModel.getAllEmployees().observe(getViewLifecycleOwner(), employees -> {
			employeeAdapter.setEmployeeList(employees);
		});
	}

	private void setupSearchView() {
		binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false; // No separate handling needed
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (binding.recyclerViewExpense.getVisibility() == View.VISIBLE) {
					expenseAdapter.filter(newText); // Filter Expense list
				} else if (binding.recyclerViewEmployee.getVisibility() == View.VISIBLE) {
					employeeAdapter.filter(newText); // Filter Employee list
				}
				return true;
			}
		});
	}

	private void setupViewToggleButtons() {
		binding.buttonExpenseView.setOnClickListener(v -> showExpenseView());
		binding.buttonEmployeeView.setOnClickListener(v -> showEmployeeView());
	}

	private void showExpenseView() {
		binding.recyclerViewExpense.setVisibility(View.VISIBLE);
		binding.recyclerViewEmployee.setVisibility(View.GONE);
		binding.searchView.setQueryHint("Search Expenses"); // Set SearchView hint
	}

	private void showEmployeeView() {
		binding.recyclerViewExpense.setVisibility(View.GONE);
		binding.recyclerViewEmployee.setVisibility(View.VISIBLE);
		binding.searchView.setQueryHint("Search Employees"); // Set SearchView hint
	}

	private void setupFloatingActionButton() {
		binding.fabAddOptions.setOnClickListener(view -> {
			// Toggle visibility of the circular menu
			if (binding.circularMenu.getVisibility() == View.GONE) {
				binding.circularMenu.setVisibility(View.VISIBLE);
				binding.circularMenu.setAlpha(0f);
				binding.circularMenu.animate().alpha(1f).setDuration(300).start();
			} else {
				binding.circularMenu.animate().alpha(0f).setDuration(300).withEndAction(() -> {
					binding.circularMenu.setVisibility(View.GONE);
				}).start();
			}
		});

		// Handle button clicks in the circular menu
		binding.actionAddSale.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(), AddInvoiceActivity.class));
		});

		binding.actionViewSales.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(), ViewInvoicesActivity.class));
		});

		binding.actionAddParty.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(), AddPartyActivity.class));
		});
	}
}
