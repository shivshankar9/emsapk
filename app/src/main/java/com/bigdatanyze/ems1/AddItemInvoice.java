package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bigdatanyze.ems1.model.Item;

import java.util.ArrayList;

public class AddItemInvoice extends AppCompatActivity {

	private EditText etItemName, etItemQty, etItemPrice;
	private Button btnSaveItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item_invoice);

		etItemName = findViewById(R.id.etItemName);
		etItemQty = findViewById(R.id.etItemQty);
		etItemPrice = findViewById(R.id.etItemPrice);
		btnSaveItem = findViewById(R.id.btnSaveItem);

		btnSaveItem.setOnClickListener(v -> {
			// Get the item details from the input fields
			String itemName = etItemName.getText().toString();
			int quantity = Integer.parseInt(etItemQty.getText().toString());
			double price = Double.parseDouble(etItemPrice.getText().toString());
			double totalPrice = quantity * price;

			// Create an Intent to send data back to AddInvoiceActivity
			Intent resultIntent = new Intent();
			resultIntent.putExtra("ITEM_NAME", itemName);
			resultIntent.putExtra("ITEM_QTY", quantity);
			resultIntent.putExtra("ITEM_PRICE", price);
			resultIntent.putExtra("ITEM_TOTAL_PRICE", totalPrice);

			// Set the result and finish the activity
			setResult(RESULT_OK, resultIntent);
			finish();
		});
	}
}
