package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.Item;
import com.bigdatanyze.ems1.viewmodel.ItemViewModel;

public class AddItemActivity extends AppCompatActivity {

	private EditText itemNameInput, itemPriceInput;
	private Button saveItemButton;
	private ItemViewModel itemViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);

		// Initialize views
		itemNameInput = findViewById(R.id.itemNameInput);
		itemPriceInput = findViewById(R.id.itemPriceInput);
		saveItemButton = findViewById(R.id.saveItemButton);

		// Initialize ViewModel
		itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

		// Handle save button click
		saveItemButton.setOnClickListener(v -> {
			String itemName = itemNameInput.getText().toString();
			String itemPriceStr = itemPriceInput.getText().toString();

			if (itemName.isEmpty() || itemPriceStr.isEmpty()) {
				Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
			} else {
				double itemPrice = Double.parseDouble(itemPriceStr);
				Item item = new Item(itemName, itemPrice);
				itemViewModel.insert(item);
				Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
				finish();  // Go back to the previous screen
			}
		});
	}
}
