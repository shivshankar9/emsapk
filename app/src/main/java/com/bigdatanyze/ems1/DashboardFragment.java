
package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.ExpenseAdapter;
import com.bigdatanyze.ems1.databinding.FragmentDashboardBinding;
import com.bigdatanyze.ems1.viewmodel.ExpenseViewModel;

public class DashboardFragment extends Fragment {

	private FragmentDashboardBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentDashboardBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Set up Expense RecyclerView
		binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));
		ExpenseAdapter expenseAdapter = new ExpenseAdapter();
		binding.recyclerViewExpense.setAdapter(expenseAdapter);

		// Set up Expense ViewModel
		ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
		expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
			expenseAdapter.setExpenseList(expenses);
		});

		return view;
	}
}
