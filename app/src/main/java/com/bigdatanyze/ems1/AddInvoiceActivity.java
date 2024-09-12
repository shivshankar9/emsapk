package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.InvoiceItemAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.InvoiceItem;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddInvoiceActivity extends AppCompatActivity {

	private EditText invoiceNumberEditText, customerNameEditText, customerContactEditText, dateEditText, notesEditText;
	private EditText itemNameEditText, quantityEditText, unitPriceEditText;
	private RecyclerView itemsRecyclerView;
	private InvoiceItemAdapter itemAdapter;
	private List<InvoiceItem> invoiceItemList;
	private InvoiceViewModel invoiceViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_invoice);

		// Initialize UI components
		initializeUIComponents();

		// Initialize ViewModel
		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Set up click listeners for buttons
		setupClickListeners();
	}

	/**
	 * Initialize UI components and RecyclerView.
	 */
	private void initializeUIComponents() {
		invoiceNumberEditText = findViewById(R.id.invoice_number_edit_text);
		customerNameEditText = findViewById(R.id.customer_name_edit_text);
		customerContactEditText = findViewById(R.id.customer_contact_edit_text);
		dateEditText = findViewById(R.id.date_edit_text);
		notesEditText = findViewById(R.id.notes_edit_text);
		itemNameEditText = findViewById(R.id.item_name_edit_text);
		quantityEditText = findViewById(R.id.quantity_edit_text);
		unitPriceEditText = findViewById(R.id.unit_price_edit_text);
		Button addItemButton = findViewById(R.id.add_item_button);
		Button saveInvoiceButton = findViewById(R.id.save_invoice_button);
		itemsRecyclerView = findViewById(R.id.items_recycler_view);

		// Initialize the list and adapter for invoice items
		invoiceItemList = new ArrayList<>();
		itemAdapter = new InvoiceItemAdapter(invoiceItemList);
		itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		itemsRecyclerView.setAdapter(itemAdapter);
	}

	/**
	 * Set up click listeners for the "Add Item" and "Save Invoice" buttons.
	 */
	private void setupClickListeners() {
		findViewById(R.id.add_item_button).setOnClickListener(v -> addItemToInvoice());
		findViewById(R.id.save_invoice_button).setOnClickListener(v -> saveInvoice());
	}

	/**
	 * Adds an item to the invoice item list.
	 */
	private void addItemToInvoice() {
		String itemName = itemNameEditText.getText().toString().trim();
		String quantityStr = quantityEditText.getText().toString().trim();
		String unitPriceStr = unitPriceEditText.getText().toString().trim();

		// Validate input fields
		if (validateItemInputs(itemName, quantityStr, unitPriceStr)) {
			int quantity = Integer.parseInt(quantityStr);
			double unitPrice = Double.parseDouble(unitPriceStr);
			InvoiceItem item = new InvoiceItem(itemName, quantity, unitPrice);

			// Add item to list and update RecyclerView
			invoiceItemList.add(item);
			itemAdapter.notifyDataSetChanged();

			// Clear input fields after adding the item
			clearItemInputFields();
		}
	}

	/**
	 * Validate item inputs.
	 *
	 * @param itemName Item name
	 * @param quantityStr Quantity as string
	 * @param unitPriceStr Unit price as string
	 * @return true if valid, false otherwise
	 */
	private boolean validateItemInputs(String itemName, String quantityStr, String unitPriceStr) {
		if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(quantityStr) || TextUtils.isEmpty(unitPriceStr)) {
			Toast.makeText(this, "Please fill out all item details", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * Clears the input fields for adding an item.
	 */
	private void clearItemInputFields() {
		itemNameEditText.setText("");
		quantityEditText.setText("");
		unitPriceEditText.setText("");
	}

	/**
	 * Saves the invoice.
	 */
	private void saveInvoice() {
		String invoiceNumber = invoiceNumberEditText.getText().toString().trim();
		String customerName = customerNameEditText.getText().toString().trim();
		String customerContact = customerContactEditText.getText().toString().trim();
		String date = dateEditText.getText().toString().trim();
		String notes = notesEditText.getText().toString().trim();

		// Validate input fields
		if (validateInvoiceInputs(invoiceNumber, customerName, customerContact, date)) {
			double totalAmount = calculateTotalAmount();

			Invoice invoice = new Invoice(invoiceNumber, customerName, customerContact, date, totalAmount, invoiceItemList, notes);

			// Save the invoice using ViewModel
			invoiceViewModel.insert(invoice);
			Toast.makeText(this, "Invoice saved successfully", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	/**
	 * Validate invoice inputs.
	 *
	 * @param invoiceNumber Invoice number
	 * @param customerName Customer name
	 * @param customerContact Customer contact
	 * @param date Date
	 * @return true if valid, false otherwise
	 */
	private boolean validateInvoiceInputs(String invoiceNumber, String customerName, String customerContact, String date) {
		if (TextUtils.isEmpty(invoiceNumber) || TextUtils.isEmpty(customerName) || TextUtils.isEmpty(customerContact) || TextUtils.isEmpty(date)) {
			Toast.makeText(this, "Please fill out all invoice details", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * Calculates the total amount for the invoice.
	 *
	 * @return Total amount
	 */
	private double calculateTotalAmount() {
		double totalAmount = 0.0;
		for (InvoiceItem item : invoiceItemList) {
			totalAmount += item.getTotalPrice();
		}
		return totalAmount;
	}
}
