package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout using ViewBinding
		binding = FragmentMenuBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Initialize file picker for restore functionality
		filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onFilePicked);

		// Set up dropdown menus for each spinner
		setupDropdownMenus();

		return view;
	}

	// Method to set up dropdown menus for each spinner
	private void setupDropdownMenus() {
		setupSpinner(binding.spinnerSales, R.array.sales_options);
		setupSpinner(binding.spinnerViewItems, R.array.item_options);
		setupSpinner(binding.spinnerParty, R.array.party_options);
		setupSpinner(binding.spinnerPurchase, R.array.purchase_options);
		setupSpinner(binding.spinnerBackupRestore, R.array.backup_restore_options);

		binding.buttonAddEmployee.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddEmployeeActivity.class);
			startActivity(intent);
		});
	}

	// Method to configure a spinner with options
	private void setupSpinner(Spinner spinner, int arrayResource) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
				arrayResource, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			private boolean isFirstSelection = true; // Flag to track initial selection

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (isFirstSelection) {
					isFirstSelection = false; // Skip action for the initial selection
					return;
				}
				handleSpinnerSelection(spinner, position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
			}
		});
	}

	// Handle spinner selections
	private void handleSpinnerSelection(Spinner spinner, int position) {
		if (spinner == binding.spinnerSales) {
			if (position == 0) {
				startActivity(new Intent(getActivity(), AddInvoiceActivity.class));
			} else if (position == 1) {
				startActivity(new Intent(getActivity(), ViewInvoicesActivity.class));
			}
		} else if (spinner == binding.spinnerViewItems) {
			if (position == 0) {
				startActivity(new Intent(getActivity(), AddItemActivity.class));
			} else if (position == 1) {
				startActivity(new Intent(getActivity(), ViewItemsActivity.class));
			}
		} else if (spinner == binding.spinnerParty) {
			if (position == 0) {
				startActivity(new Intent(getActivity(), AddPartyActivity.class));
			} else if (position == 1) {
				startActivity(new Intent(getActivity(), ViewPartyActivity.class));
			}
		} else if (spinner == binding.spinnerPurchase) {
			// Handle purchase options if needed
		} else if (spinner == binding.spinnerBackupRestore) {
			if (position == 0) {
				DatabaseBackup.backupDatabase(requireContext());
			} else if (position == 1) {
				filePickerLauncher.launch("application/octet-stream");
			}
		}
	}

	// Handle file selection for database restore
	private void onFilePicked(Uri uri) {
		if (uri != null) {
			DatabaseBackup.restoreDatabase(requireContext(), uri);
		} else {
			Toast.makeText(requireContext(), "No file selected.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
