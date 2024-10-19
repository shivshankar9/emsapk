package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.SearchView;
import com.bigdatanyze.ems1.adapter.ItemAdapter;
import com.bigdatanyze.ems1.adapter.PartyAdapter;
import com.bigdatanyze.ems1.databinding.FragmentHomeBinding;
import com.bigdatanyze.ems1.viewmodel.ItemViewModel;
import com.bigdatanyze.ems1.viewmodel.PartyViewModel;

public class HomeFragment extends Fragment {

	private FragmentHomeBinding binding;
	private ItemAdapter itemAdapter;
	private PartyAdapter partyAdapter;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		setupRecyclerViews();
		setupViewModels();
		setupSearchView();
		setupViewToggleButtons();
		setupFloatingActionButton();

		// Default view to display Parties first
		showPartyView();

		return view;
	}

	private void setupRecyclerViews() {
		binding.recyclerViewParties.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));

		// Initialize adapters
		partyAdapter = new PartyAdapter();
		itemAdapter = new ItemAdapter();

		// Set adapters to RecyclerViews
		binding.recyclerViewParties.setAdapter(partyAdapter);
		binding.recyclerViewItems.setAdapter(itemAdapter);
	}

	private void setupViewModels() {
		PartyViewModel partyViewModel = new ViewModelProvider(this).get(PartyViewModel.class);
		ItemViewModel itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

		// Observe Party and Item data and update adapters
		partyViewModel.getAllParties().observe(getViewLifecycleOwner(), parties -> {
			partyAdapter.setPartyList(parties);
		});

		itemViewModel.getAllItems().observe(getViewLifecycleOwner(), items -> {
			itemAdapter.setItems(items);
		});
	}

	private void setupSearchView() {
		binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false; // No separate handling needed
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// Filter based on which view is currently visible
				if (binding.recyclerViewParties.getVisibility() == View.VISIBLE) {
					partyAdapter.getFilter().filter(newText); // Filter Party list
				} else if (binding.recyclerViewItems.getVisibility() == View.VISIBLE) {
					itemAdapter.getFilter().filter(newText); // Filter Item list
				}
				return true;
			}
		});
	}

	private void setupViewToggleButtons() {
		binding.buttonPartyView.setOnClickListener(v -> {
			showPartyView();
			binding.buttonPartyView.setAlpha(1f);
			binding.buttonItemsView.setAlpha(0.7f);
		});

		binding.buttonItemsView.setOnClickListener(v -> {
			showItemView();
			binding.buttonItemsView.setAlpha(1f);
			binding.buttonPartyView.setAlpha(0.7f);
		});
	}

	private void showPartyView() {
		binding.recyclerViewParties.setVisibility(View.VISIBLE);
		binding.recyclerViewItems.setVisibility(View.GONE);
		binding.searchView.setQueryHint("Search Parties");

		// Fade-in effect
		binding.recyclerViewParties.setAlpha(0f);
		binding.recyclerViewParties.animate().alpha(1f).setDuration(300).start();
	}

	private void showItemView() {
		binding.recyclerViewParties.setVisibility(View.GONE);
		binding.recyclerViewItems.setVisibility(View.VISIBLE);
		binding.searchView.setQueryHint("Search Items");

		// Fade-in effect
		binding.recyclerViewItems.setAlpha(0f);
		binding.recyclerViewItems.animate().alpha(1f).setDuration(300).start();
	}

	private void setupFloatingActionButton() {
		binding.fabAddOptions.setOnClickListener(view -> {
			if (binding.circularMenu.getVisibility() == View.GONE) {
				binding.circularMenu.setVisibility(View.VISIBLE);
				binding.circularMenu.setAlpha(0f);
				binding.circularMenu.animate().alpha(1f).setDuration(300).start();
			} else {
				binding.circularMenu.animate().alpha(0f).setDuration(300).withEndAction(() -> {
					binding.circularMenu.setVisibility(View.GONE);
				}).start();
			}
		});

		binding.actionAddSale.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(), AddInvoiceActivity.class));
		});

		binding.actionViewSales.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(), ViewInvoicesActivity.class));
		});

		binding.actionAddParty.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(), AddPartyActivity.class));
		});
	}
}
