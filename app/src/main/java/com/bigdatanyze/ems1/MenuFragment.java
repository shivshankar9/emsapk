package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);

		// Set up click listeners for all buttons
		view.findViewById(R.id.addSalesButton).setOnClickListener(v -> openActivity(AddInvoiceActivity.class));
		view.findViewById(R.id.viewSalesButton).setOnClickListener(v -> openActivity(ViewInvoicesActivity.class));
		view.findViewById(R.id.addItemsButton).setOnClickListener(v -> openActivity(AddItemActivity.class));
		view.findViewById(R.id.viewItemsButton).setOnClickListener(v -> openActivity(ViewItemsActivity.class));
		view.findViewById(R.id.addPartyButtons).setOnClickListener(v -> openActivity(AddPartyActivity.class));
		view.findViewById(R.id.viewClientButton).setOnClickListener(v -> openActivity(ViewPartyActivity.class));
		view.findViewById(R.id.AddExpenseButton).setOnClickListener(v -> openActivity(AddExpenseActivity.class));
		view.findViewById(R.id.viewItemsButton).setOnClickListener(v -> openActivity(ViewItemsActivity.class));

		return view;
	}

	/**
	 * Opens the specified activity.
	 *
	 * @param activityClass The class of the activity to open.
	 */
	private void openActivity(Class<?> activityClass) {
		Intent intent = new Intent(getActivity(), activityClass);
		startActivity(intent);
	}
}
