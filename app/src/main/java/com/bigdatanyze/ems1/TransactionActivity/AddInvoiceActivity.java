package com.bigdatanyze.ems1.TransactionActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.AddItemInvoice;
import com.bigdatanyze.ems1.InvoicePreviewActivity;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.invoiceReadyPage;
import com.bigdatanyze.ems1.model.Item;
import com.bigdatanyze.ems1.model.Party;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddInvoiceActivity extends AppCompatActivity {

	private ListView lvInvoiceItems;
	private ArrayList<Item> itemList;
	private ArrayAdapter<Item> adapter;
	private EditText invoiceNumberEditText, dateEditText, dueDateEditText;
	private AutoCompleteTextView customerNameAutoComplete, itemNameAutoComplete;
	private EditText customerContactEditText; // Declare the EditText for customer contact
	private InvoiceViewModel invoiceViewModel;
	private ArrayAdapter<String> customerAdapter, itemAdapterForAutoComplete;

	private static final int ADD_ITEM_REQUEST = 1; // Request code for adding an item

	@SuppressLint({"WrongViewCast", "MissingInflatedId"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_invoice);

		// Initialize EditText views
		invoiceNumberEditText = findViewById(R.id.etInvoiceNumber);
		dateEditText = findViewById(R.id.invoiceDateTextView);
		dueDateEditText = findViewById(R.id.etDate);  // Assuming you have a due date field
		customerNameAutoComplete = findViewById(R.id.etCustomerName);
//		customerContactEditText = findViewById(R.id.etCustomerContact); // Initialize customer contact EditText

		// Initialize ListView and other UI components
		lvInvoiceItems = findViewById(R.id.lvInvoiceItems);
		itemList = new ArrayList<>();

		// Call methods to set values for invoice number and date
		generateDefaultInvoiceNumber();
		generateCurrentDate();

		// Initialize the adapter for the ListView
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
			Intent intent = new Intent(AddInvoiceActivity.this, AddItemInvoice.class);
			startActivityForResult(intent, ADD_ITEM_REQUEST);
		});

		TextView btnPreview = findViewById(R.id.btnPreview);
		btnPreview.setOnClickListener(v -> {
			Intent intent = new Intent(AddInvoiceActivity.this, InvoicePreviewActivity.class);
			startActivity(intent);
		});

		Button btnSaveInvoice1 = findViewById(R.id.btnSaveInvoice);
		btnSaveInvoice1.setOnClickListener(v -> {
			Intent intent = new Intent(AddInvoiceActivity.this, invoiceReadyPage.class);
			startActivity(intent);
		});

		// Back button functionality
		ImageView btnBack = findViewById(R.id.btnBack);
		btnBack.setOnClickListener(v -> finish());

		// Setup ViewModel
		setupViewModel();
	}

	private void generateDefaultInvoiceNumber() {
		if (invoiceViewModel != null) {
			// Observe the last invoice number LiveData
			invoiceViewModel.getLastInvoiceNumber().observe(this, lastInvoiceNumber -> {
				try {
					if (lastInvoiceNumber != null && !lastInvoiceNumber.isEmpty()) {
						String cleanedNumber = lastInvoiceNumber.replaceAll("[^0-9]", "");
						int nextNumber = cleanedNumber.isEmpty() ? 1001 : Integer.parseInt(cleanedNumber) + 1;
						invoiceNumberEditText.setText(String.format(Locale.getDefault(), "%d", nextNumber));
					} else {
						invoiceNumberEditText.setText("1001");
					}
				} catch (NumberFormatException e) {
					invoiceNumberEditText.setText("1001");
					Toast.makeText(this, "Error generating invoice number, defaulting to 1001", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			invoiceNumberEditText.setText("1001");
			Toast.makeText(this, "InvoiceViewModel not initialized, defaulting to 1001", Toast.LENGTH_SHORT).show();
		}
	}

	private void generateCurrentDate() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
		dateEditText.setText(currentDate);
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

	private void setupViewModel() {
		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Fetch existing customer names
		invoiceViewModel.getAllParties().observe(this, new Observer<List<Party>>() {
			@Override
			public void onChanged(List<Party> parties) {
				List<String> partyNames = new ArrayList<>();
				for (Party party : parties) {
					partyNames.add(party.getName());
				}

				customerAdapter = new ArrayAdapter<>(AddInvoiceActivity.this,
						android.R.layout.simple_dropdown_item_1line, partyNames);
				customerNameAutoComplete.setAdapter(customerAdapter);

				// Set listener to fetch customer contact
				customerNameAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
					String selectedName = customerAdapter.getItem(position);
					for (Party party : parties) {
						if (party.getName().equals(selectedName)) {
							// Set the customer contact in the corresponding EditText
							customerContactEditText.setText(party.getContact());
							break;
						}
					}
				});
			}
		});
	}
}
