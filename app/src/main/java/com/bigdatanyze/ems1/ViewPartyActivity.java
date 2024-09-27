package com.bigdatanyze.ems1;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.View; // Import View
import android.widget.Button; // Import Button
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
	private Button buttonAddParty; // Declare the button

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

		// Initialize and set onClickListener for Add Party button
		buttonAddParty = findViewById(R.id.buttonAddParty); // Assuming you have this button in your layout
		buttonAddParty.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewPartyActivity.this, AddPartyActivity.class);
				startActivity(intent); // Start AddPartyActivity
			}
		});
	}
}
