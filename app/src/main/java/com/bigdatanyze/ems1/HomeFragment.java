package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
import com.bigdatanyze.ems1.databinding.FragmentHomeBinding;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;

public class HomeFragment extends Fragment {

	private FragmentHomeBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Set up Employee RecyclerView
		binding.recyclerViewEmployee.setLayoutManager(new LinearLayoutManager(getContext()));
		EmployeeAdapter employeeAdapter = new EmployeeAdapter();
		binding.recyclerViewEmployee.setAdapter(employeeAdapter);

		// Set up Employee ViewModel
		EmployeeViewModel employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
		employeeViewModel.getAllEmployees().observe(getViewLifecycleOwner(), employees -> {
			employeeAdapter.setEmployeeList(employees);
		});

		return view;
	}
}
