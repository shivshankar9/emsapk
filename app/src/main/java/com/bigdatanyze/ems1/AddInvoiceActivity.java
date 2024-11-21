package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.InvoiceItem;
import com.bigdatanyze.ems1.model.Item;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class AddInvoiceActivity extends AppCompatActivity {

	private ListView lvInvoiceItems;
	private ArrayList<Item> itemList;
	private ArrayAdapter<Item> adapter;
	private static final int ADD_ITEM_REQUEST = 1; // Request code for adding an item

	@SuppressLint({"WrongViewCast", "MissingInflatedId"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_invoice);

		lvInvoiceItems = findViewById(R.id.lvInvoiceItems);
		itemList = new ArrayList<>();

		// Setting up the adapter to display items in the list
		adapter = new ArrayAdapter<Item>(this, R.layout.item_invoice, itemList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_invoice, parent, false);
				}

				Item item = getItem(position);

				TextView tvItemName = convertView.findViewById(R.id.tvItemName);
				TextView tvItemQty = convertView.findViewById(R.id.tvItemQty);
				TextView tvItemTotal = convertView.findViewById(R.id.tvItemTotal);

				tvItemName.setText(item.getItemName());
				tvItemQty.setText("Nos " + item.getQuantity() + " x ₹" + item.getItemPrice());
				tvItemTotal.setText("₹" + item.getTotalPrice());

				return convertView;
			}
		};

		lvInvoiceItems.setAdapter(adapter);

		// Button to add a new item
		Button btnAddItem = findViewById(R.id.btnAddItem);
		btnAddItem.setOnClickListener(v -> {
			// Start the AddItemInvoice activity to add a new item
			Intent intent = new Intent(AddInvoiceActivity.this, AddItemInvoice.class);
			startActivityForResult(intent, ADD_ITEM_REQUEST);
		});
		TextView btnPreview = findViewById(R.id.btnPreview);
		btnPreview.setOnClickListener(v -> {
			// Start the InvoicePreviewActivity
			Intent intent = new Intent(AddInvoiceActivity.this, InvoicePreviewActivity.class);
			startActivity(intent);
		});

		//save buuton to preview
		Button btnSaveInvoice1 = findViewById(R.id.btnSaveInvoice);
		btnSaveInvoice1.setOnClickListener(v -> {
			// Start the AddItemInvoice activity to add a new item
			Intent intent = new Intent(AddInvoiceActivity.this, invoiceReadyPage.class);
//			startActivityForResult(intent, ADD_ITEM_REQUEST);
			startActivity(intent);
		});


// Back button functionality
		ImageView btnBack = findViewById(R.id.btnBack);
		btnBack.setOnClickListener(v -> {
			// Navigate back to the previous activity
			finish();
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
			// Get the item data from AddItemInvoice activity
			String itemName = data.getStringExtra("ITEM_NAME");
			int quantity = data.getIntExtra("ITEM_QTY", 0);
			double price = data.getDoubleExtra("ITEM_PRICE", 0.0);
			double totalPrice = data.getDoubleExtra("ITEM_TOTAL_PRICE", 0.0);

			// Log the item data to make sure it's being received correctly
			Log.d("AddInvoiceActivity", "Adding item: " + itemName + ", Quantity: " + quantity);

			// Create a new Item object and add it to the list
			Item newItem = new Item(itemName, quantity, price, totalPrice);

			// Ensure the itemList is not null and add the item to the list
			if (itemList == null) {
				itemList = new ArrayList<>();
			}
			itemList.add(newItem);

			// Log the current number of items in the list
			Log.d("AddInvoiceActivity", "Current number of items: " + itemList.size());

			// Notify the adapter to update the list view
			adapter.notifyDataSetChanged();
		}
	}
}
