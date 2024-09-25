package com.bigdatanyze.ems1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import com.bigdatanyze.ems1.database.DatabaseBackup;
import com.bigdatanyze.ems1.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {

	private FragmentMenuBinding binding;
	private ActivityResultLauncher<String> filePickerLauncher;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment using View Binding
		binding = FragmentMenuBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Register file picker for restore database functionality
		filePickerLauncher = registerForActivityResult(
				new ActivityResultContracts.GetContent(),
				this::onFilePicked
		);

		// Set click listeners for the buttons
		binding.buttonAddEmployee.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddEmployeeActivity.class);
			startActivity(intent);
		});
		binding.buttonAddPartyButton.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddPartyActivity.class);
			startActivity(intent);
		});

		binding.buttonAddExpense.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
			startActivity(intent);
		});

		binding.buttonAddInvoice.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddInvoiceActivity.class);
			startActivity(intent);
		});

		binding.buttonViewInvoices.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), ViewInvoicesActivity.class);
			startActivity(intent);
		});
		binding.buttonViewPary.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), ViewPartyActivity.class);
			startActivity(intent);
		});

		// Handle backup button click
		binding.buttonBackupDatabase.setOnClickListener(v -> {
			DatabaseBackup.backupDatabase(requireContext());
		});

		// Handle restore button click - launch file picker
		binding.buttonRestoreDatabase.setOnClickListener(v -> {
			filePickerLauncher.launch("application/octet-stream"); // Expecting DB files
		});

		return view;
	}

	// Handle the result from the file picker
	private void onFilePicked(Uri uri) {
		if (uri != null) {
			DatabaseBackup.restoreDatabase(requireContext(), uri);
		} else {
			// Handle case where no file was picked
			Toast.makeText(requireContext(), "No file selected.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;  // Prevent memory leaks
	}
}
