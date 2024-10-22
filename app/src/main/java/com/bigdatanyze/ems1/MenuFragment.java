package com.bigdatanyze.ems1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.bigdatanyze.ems1.database.DatabaseBackup;
import com.bigdatanyze.ems1.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {

	private FragmentMenuBinding binding;
	private ActivityResultLauncher<String> filePickerLauncher;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize file picker for restore functionality
		filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onFilePicked);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout using ViewBinding
		binding = FragmentMenuBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Set up button listeners
		setupButtonListeners();

		return view;
	}

	// Method to set up button click listeners
	private void setupButtonListeners() {
		// Sales button listener with options (Add or View)
		binding.buttonSales.setOnClickListener(v -> showSalesOptions());

		// Items button listener with options (Add or View)
		binding.buttonItems.setOnClickListener(v -> showItemOptions());

		// Party button listener with options (Add or View)
		binding.buttonParty.setOnClickListener(v -> showPartyOptions());

		// Backup/Restore button listener
		binding.buttonBackupRestore.setOnClickListener(v -> showBackupRestoreOptions());
	}

	// Show options for Sales (Add or View)
	private void showSalesOptions() {
		String[] options = {"Add Sales", "View Sales"};
		new AlertDialog.Builder(requireContext())
				.setTitle("Choose an option")
				.setItems(options, (dialog, which) -> {
					if (which == 0) {
						startActivity(new Intent(getActivity(), AddInvoiceActivity.class)); // Add Sales
					} else if (which == 1) {
						startActivity(new Intent(getActivity(), ViewInvoicesActivity.class)); // View Sales
					}
				})
				.show();
	}

	// Show options for Items (Add or View)
	private void showItemOptions() {
		String[] options = {"Add Item", "View Items"};
		new AlertDialog.Builder(requireContext())
				.setTitle("Choose an option")
				.setItems(options, (dialog, which) -> {
					if (which == 0) {
						startActivity(new Intent(getActivity(), AddItemActivity.class)); // Add Item
					} else if (which == 1) {
						startActivity(new Intent(getActivity(), ViewItemsActivity.class)); // View Items
					}
				})
				.show();
	}

	// Show options for Party (Add or View)
	private void showPartyOptions() {
		String[] options = {"Add Party", "View Party"};
		new AlertDialog.Builder(requireContext())
				.setTitle("Choose an option")
				.setItems(options, (dialog, which) -> {
					if (which == 0) {
						startActivity(new Intent(getActivity(), AddPartyActivity.class)); // Add Party
					} else if (which == 1) {
						startActivity(new Intent(getActivity(), ViewPartyActivity.class)); // View Party
					}
				})
				.show();
	}

	// Show options for backup and restore functionality
	private void showBackupRestoreOptions() {
		String[] options = {"Backup", "Restore"};
		new AlertDialog.Builder(requireContext())
				.setTitle("Choose an option")
				.setItems(options, (dialog, which) -> {
					if (which == 0) {
						DatabaseBackup.backupDatabase(requireContext());
					} else if (which == 1) {
						filePickerLauncher.launch("application/octet-stream");
					}
				})
				.show();
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
