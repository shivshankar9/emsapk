package com.bigdatanyze.ems1.MenuActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.MainActivity;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.adapter.RecentActivityAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;
import com.bigdatanyze.ems1.databinding.FragmentDashboardBinding;

import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {

	private FragmentDashboardBinding binding;
	private InvoiceViewModel invoiceViewModel;
	private TextView totalRevenueTextView;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentDashboardBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		totalRevenueTextView = binding.totalRevenueTextView; // Add a TextView in your XML for showing total revenue

		setupRecentActivityRecyclerView();
		setupBottomNavigation();

		// Initialize ViewModel
		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Observe the LiveData for invoices and update total revenue
		invoiceViewModel.getAllInvoices().observe(getViewLifecycleOwner(), this::updateTotalRevenue);

		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		// Hide toolbar when HomeFragment is visible
		((MainActivity) requireActivity()).hideToolbar();
	}



	// Method to calculate and update total revenue
	private void updateTotalRevenue(List<Invoice> invoices) {
		double totalRevenue = 0.0;
		if (invoices != null && !invoices.isEmpty()) {
			for (Invoice invoice : invoices) {
				totalRevenue += invoice.getTotalAmount();
			}
			totalRevenueTextView.setText(String.format("$%.2f", totalRevenue));
		} else {
			totalRevenueTextView.setText("Total Revenue: $0.00");
		}
	}

	private void setupRecentActivityRecyclerView() {
		binding.recyclerViewRecentActivity.setLayoutManager(new LinearLayoutManager(getContext()));
		RecentActivityAdapter adapter = new RecentActivityAdapter(getRecentActivities());
		binding.recyclerViewRecentActivity.setAdapter(adapter);
	}

	private void setupBottomNavigation() {
		binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.nav_home) {
				return true;
			} else if (item.getItemId() == R.id.nav_inventory) {
				return true;
			} else if (item.getItemId() == R.id.nav_invoices) {
				return true;
			} else if (item.getItemId() == R.id.nav_reports) {
				return true;
			} else {
				return false;
			}
		});
	}

	private List<String> getRecentActivities() {
		return Arrays.asList("Added item A", "Removed item B", "Updated item C");
	}
}