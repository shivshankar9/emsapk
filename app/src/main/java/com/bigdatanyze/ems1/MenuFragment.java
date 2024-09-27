package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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

		// Set up dropdown menus for each button
		setupDropdownMenus();

		return view;
	}

	// Method to set up dropdown menus for each button
	private void setupDropdownMenus() {
		binding.buttonSales.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_sales)); // Sales menu
		binding.buttonParty.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_party));
		binding.buttonPurchase.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_purchase));
		binding.buttonBackupRestore.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_backup_restore));
		binding.buttonViewItems.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_item));

		binding.buttonAddEmployee.setOnClickListener(v -> {
			Intent intent = new Intent(getActivity(), AddEmployeeActivity.class);
			startActivity(intent);
		});
	}

	// Method to display the dropdown (PopupMenu)
	private void showPopupMenu(View view, int menuResource) {
		PopupMenu popupMenu = new PopupMenu(getActivity(), view);
		popupMenu.getMenuInflater().inflate(menuResource, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(this::handleMenuItemClick);
		popupMenu.show();
	}

	@SuppressLint("NonConstantResourceId")
	private boolean handleMenuItemClick(MenuItem item) {
		if (item.getItemId() == R.id.action_add_sale) {
			startActivity(new Intent(getActivity(), AddInvoiceActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_view_sales) {
			startActivity(new Intent(getActivity(), ViewInvoicesActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_add_party) {
			startActivity(new Intent(getActivity(), AddPartyActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_view_party) {
			startActivity(new Intent(getActivity(), ViewPartyActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_add_item) {
			startActivity(new Intent(getActivity(), AddItemActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_view_item) {
			startActivity(new Intent(getActivity(), ViewItemsActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_add_expense) {
			startActivity(new Intent(getActivity(), AddExpenseActivity.class));
			return true;
		} else if (item.getItemId() == R.id.action_backup_database) {
			DatabaseBackup.backupDatabase(requireContext());
			return true;
		} else if (item.getItemId() == R.id.action_restore_database) {
			filePickerLauncher.launch("application/octet-stream");
			return true;
		} else {
			return false;
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
