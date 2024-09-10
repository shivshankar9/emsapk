package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.bigdatanyze.ems1.AddEmployeeActivity;
import com.bigdatanyze.ems1.AddExpenseActivity;
import com.bigdatanyze.ems1.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {

	private FragmentMenuBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentMenuBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		binding.buttonAddEmployee.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddEmployeeActivity.class);
			startActivity(intent);
		});

		binding.buttonAddExpense.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
			startActivity(intent);
		});

		return view;
	}
}
