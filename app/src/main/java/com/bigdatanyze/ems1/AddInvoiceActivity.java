package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.InvoiceItemAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.InvoiceItem;
import com.bigdatanyze.ems1.model.Party;
import com.bigdatanyze.ems1.model.Item;  // Import Item for item selection
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddInvoiceActivity extends AppCompatActivity {

	private AutoCompleteTextView customerNameAutoComplete, itemNameAutoComplete;
	private EditText invoiceNumberEditText, customerContactEditText, dateEditText, notesEditText;
	private EditText quantityEditText, unitPriceEditText;
	private RecyclerView itemsRecyclerView;
	private InvoiceItemAdapter itemAdapter;
	private List<InvoiceItem> invoiceItemList;
	private InvoiceViewModel invoiceViewModel;
	private ArrayAdapter<String> customerAdapter, itemAdapterForAutoComplete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_invoice);

		initializeUIComponents();
		setupViewModel();
		generateDefaultInvoiceNumber();
		generateCurrentDate();
		setupClickListeners();
	}

	private void initializeUIComponents() {
		invoiceNumberEditText = findViewById(R.id.invoice_number_edit_text);
		customerNameAutoComplete = findViewById(R.id.customer_name_auto_complete);
		customerContactEditText = findViewById(R.id.customer_contact_edit_text);
		dateEditText = findViewById(R.id.date_edit_text);
		notesEditText = findViewById(R.id.notes_edit_text);
		itemNameAutoComplete = findViewById(R.id.item_name_auto_complete);
		quantityEditText = findViewById(R.id.quantity_edit_text);
		unitPriceEditText = findViewById(R.id.unit_price_edit_text);
		Button addItemButton = findViewById(R.id.add_item_button);
		Button saveInvoiceButton = findViewById(R.id.save_invoice_button);
		itemsRecyclerView = findViewById(R.id.items_recycler_view);

		invoiceItemList = new ArrayList<>();
		itemAdapter = new InvoiceItemAdapter(invoiceItemList);
		itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		itemsRecyclerView.setAdapter(itemAdapter);
	}

	private void generateDefaultInvoiceNumber() {
		invoiceViewModel.getLastInvoiceNumber().observe(this, lastInvoiceNumber -> {
			try {
				if (lastInvoiceNumber != null) {
					String cleanedNumber = lastInvoiceNumber.replaceAll("[^0-9]", "");
					int nextNumber = cleanedNumber.isEmpty() ? 1 : Integer.parseInt(cleanedNumber) + 1;
					invoiceNumberEditText.setText(String.valueOf(nextNumber));
				} else {
					invoiceNumberEditText.setText("1");
				}
			} catch (NumberFormatException e) {
				invoiceNumberEditText.setText("1");
				Toast.makeText(this, "Error generating invoice number, defaulting to 1", Toast.LENGTH_SHORT).show();
			}

		});
	}

	private void generateCurrentDate() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
		dateEditText.setText(currentDate);
	}

	private void setupClickListeners() {
		findViewById(R.id.add_item_button).setOnClickListener(v -> addItemToInvoice());
		findViewById(R.id.save_invoice_button).setOnClickListener(v -> saveInvoice());
	}

	private void addItemToInvoice() {
		String itemName = itemNameAutoComplete.getText().toString().trim();
		String quantityStr = quantityEditText.getText().toString().trim();
		String unitPriceStr = unitPriceEditText.getText().toString().trim();

		if (validateItemInputs(itemName, quantityStr, unitPriceStr)) {
			try {
				int quantity = Integer.parseInt(quantityStr);
				double unitPrice = Double.parseDouble(unitPriceStr);
				double totalPrice = quantity * unitPrice;
				InvoiceItem item = new InvoiceItem(itemName, quantity, unitPrice, totalPrice);

				invoiceItemList.add(item);
				itemAdapter.notifyDataSetChanged();
				clearItemInputFields();
			} catch (NumberFormatException e) {
				Toast.makeText(this, "Invalid quantity or unit price format", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean validateItemInputs(String itemName, String quantityStr, String unitPriceStr) {
		if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(quantityStr) || TextUtils.isEmpty(unitPriceStr)) {
			Toast.makeText(this, "Please fill out all item details", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void clearItemInputFields() {
		itemNameAutoComplete.setText("");
		quantityEditText.setText("");
		unitPriceEditText.setText("");
	}

	private void saveInvoice() {

		String invoiceNumber = invoiceNumberEditText.getText().toString().trim();
		String customerName = customerNameAutoComplete.getText().toString().trim();
		String customerContact = customerContactEditText.getText().toString().trim();
		String date = dateEditText.getText().toString().trim();
		String notes = notesEditText.getText().toString().trim();

		if (validateInvoiceInputs(invoiceNumber, customerName, customerContact, date)) {
			double totalAmount = calculateTotalAmount();

			Invoice invoice = new Invoice(invoiceNumber, customerName, customerContact, date, totalAmount, invoiceItemList, notes);
			invoiceViewModel.insert(invoice);

			Intent intent = new Intent(this, InvoicePreviewActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("invoiceNumber", invoice.getInvoiceNumber());
			bundle.putString("customerName", invoice.getCustomerName());
			bundle.putString("customerContact", invoice.getCustomerContact());
			bundle.putString("date", invoice.getDate());
			bundle.putDouble("totalAmount", invoice.getTotalAmount());
			bundle.putString("notes", invoice.getNotes());

			ArrayList<Bundle> itemBundles = new ArrayList<>();
			for (InvoiceItem item : invoice.getItems()) {
				Bundle itemBundle = new Bundle();
				itemBundle.putString("itemName", item.getItemName());
				itemBundle.putInt("quantity", item.getQuantity());
				itemBundle.putDouble("unitPrice", item.getUnitPrice());
				itemBundle.putDouble("totalPrice", item.getTotalPrice());
				itemBundles.add(itemBundle);
			}
			bundle.putParcelableArrayList("items", itemBundles);

			intent.putExtras(bundle);
			startActivity(intent);

			Toast.makeText(this, "Invoice saved successfully", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private boolean validateInvoiceInputs(String invoiceNumber, String customerName, String customerContact, String date) {
		if (TextUtils.isEmpty(invoiceNumber) || TextUtils.isEmpty(customerName) || TextUtils.isEmpty(customerContact) || TextUtils.isEmpty(date)) {
			Toast.makeText(this, "Please fill out all invoice details", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private double calculateTotalAmount() {
		double totalAmount = 0.0;
		for (InvoiceItem item : invoiceItemList) {
			totalAmount += item.getTotalPrice();
		}
		return totalAmount;
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
							customerContactEditText.setText(party.getContact());
							break;
						}
					}
				});
			}
		});

		// Fetch existing item names and set prices
		invoiceViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
			@Override
			public void onChanged(List<Item> items) {
				List<String> itemNames = new ArrayList<>();
				for (Item item : items) {
					itemNames.add(item.getItemName());
				}

				itemAdapterForAutoComplete = new ArrayAdapter<>(AddInvoiceActivity.this,
						android.R.layout.simple_dropdown_item_1line, itemNames);
				itemNameAutoComplete.setAdapter(itemAdapterForAutoComplete);

				itemNameAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
					String selectedItemName = itemAdapterForAutoComplete.getItem(position);
					for (Item item : items) {
						if (item.getItemName().equals(selectedItemName)) {
							unitPriceEditText.setText(String.valueOf(item.getItemPrice()));
							break;
						}
					}
				});
			}
		});
	}
}
