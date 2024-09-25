package com.bigdatanyze.ems1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.ItemAdapter;
import com.bigdatanyze.ems1.viewmodel.ItemViewModel;

public class ViewItemsActivity extends AppCompatActivity {

	private ItemViewModel itemViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_items);

		// Initialize RecyclerView
		RecyclerView recyclerView = findViewById(R.id.recyclerViewItems);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);

		// Set up adapter
		ItemAdapter adapter = new ItemAdapter();
		recyclerView.setAdapter(adapter);

		// Initialize ViewModel
		itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
		itemViewModel.getAllItems().observe(this, adapter::setItems);
	}
}
