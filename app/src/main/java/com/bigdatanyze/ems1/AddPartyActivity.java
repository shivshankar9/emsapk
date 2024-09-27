package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.dao.PartyDao;
import com.bigdatanyze.ems1.model.Party;

public class AddPartyActivity extends AppCompatActivity {

	private EditText nameEditText;
	private EditText contactEditText;
	private Button saveButton;
	private PartyDao partyDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_party);

		nameEditText = findViewById(R.id.editTextName);
		contactEditText = findViewById(R.id.editTextContact);
		saveButton = findViewById(R.id.buttonSave);

		partyDao = AppDatabase.getDatabase(this).partyDao();

		saveButton.setOnClickListener(view -> saveParty());
	}

	private void saveParty() {
		String name = nameEditText.getText().toString().trim();
		String contact = contactEditText.getText().toString().trim();

		if (name.isEmpty() || contact.isEmpty()) {
			Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
			return;
		}

		Party party = new Party(name, contact);
		AppDatabase.databaseWriteExecutor.execute(() -> {
			partyDao.insert(party);
			runOnUiThread(() -> {
				Toast.makeText(this, "Party saved successfully", Toast.LENGTH_SHORT).show();
				finish(); // Close the activity
			});
		});
	}
}
