package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bigdatanyze.ems1.adapter.ItemAdapter;
import com.bigdatanyze.ems1.databinding.ActivityViewItemsBinding;
import com.bigdatanyze.ems1.viewmodel.ItemViewModel;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity {

	private ActivityViewItemsBinding binding;
	private ItemViewModel itemViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Use ViewBinding to inflate layout
		binding = ActivityViewItemsBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Set up RecyclerView with LayoutManager and Adapter
		binding.recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
		binding.recyclerViewItems.setHasFixedSize(true);

		ItemAdapter adapter = new ItemAdapter();
		binding.recyclerViewItems.setAdapter(adapter);

		// Initialize ViewModel using ViewModelProvider
		itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

		// Observe LiveData for item updates
		itemViewModel.getAllItems().observe(this, items -> {
			if (items != null) {
				adapter.setItems(items);
			} else {
				// Optionally, handle empty state (e.g., show a placeholder view)
				adapter.setItems(new ArrayList<>());
			}
		});

		// Handle Add Item button click
		binding.buttonAddItem.setOnClickListener(v -> {
			Intent intent = new Intent(ViewItemsActivity.this, AddItemActivity.class);
			startActivity(intent); // Start AddPartyActivity
			// Logic to add new item
			// Start a new activity or show a dialog for adding an item
		});
	}
}
