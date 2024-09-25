package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.PartyAdapter;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.Party;
import com.bigdatanyze.ems1.viewmodel.PartyViewModel;
import java.util.List;

public class ViewPartyActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private PartyAdapter partyAdapter;
	private PartyViewModel partyViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_party);

		// Initialize the RecyclerView
		recyclerView = findViewById(R.id.recyclerViewParties);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);

		// Initialize the Adapter
		partyAdapter = new PartyAdapter();
		recyclerView.setAdapter(partyAdapter);

		// Initialize ViewModel
		partyViewModel = new ViewModelProvider(this).get(PartyViewModel.class);

		// Observe data from ViewModel
		partyViewModel.getAllParties().observe(this, new Observer<List<Party>>() {
			@Override
			public void onChanged(List<Party> parties) {
				// Update the adapter with new data
				partyAdapter.setPartyList(parties);
			}
		});
	}
}
