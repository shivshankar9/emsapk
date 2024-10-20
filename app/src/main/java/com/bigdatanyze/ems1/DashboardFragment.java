package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.RecentActivityAdapter;
import com.bigdatanyze.ems1.databinding.FragmentDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {

	private FragmentDashboardBinding binding;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentDashboardBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		setupRecentActivityRecyclerView();

		setupBottomNavigation();

		return view;
	}

	private void setupRecentActivityRecyclerView() {
		binding.recyclerViewRecentActivity.setLayoutManager(new LinearLayoutManager(getContext()));

		// Initialize and set the adapter for recent activities
		RecentActivityAdapter adapter = new RecentActivityAdapter(getRecentActivities());
		binding.recyclerViewRecentActivity.setAdapter(adapter);
	}

//	private void setupAIButton() {
//		binding.btnAI.setOnClickListener(v -> {
//			// TODO: Implement action for AI Assistant button
//		});
//	}

	private void setupBottomNavigation() {
		binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.nav_home) {
				// Navigate to Home
				return true;
			} else if (item.getItemId() == R.id.nav_inventory) {
				// Navigate to Inventory
				return true;
			} else if (item.getItemId() == R.id.nav_invoices) {
				// Navigate to Invoices
				return true;
			} else if (item.getItemId() == R.id.nav_reports) {
				// Navigate to Reports
				return true;
			} else {
				return false;
			}
		});
	}


	// Example method to get recent activities
	private List<String> getRecentActivities() {
		// Return a list of recent activities
		return Arrays.asList("Added item A", "Removed item B", "Updated item C");
	}
}
