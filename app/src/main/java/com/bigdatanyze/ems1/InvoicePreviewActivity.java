package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.InvoiceItem;

import java.util.ArrayList;
import java.util.List;

public class InvoicePreviewActivity extends AppCompatActivity {

	private TextView invoicePreviewTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_preview);

		invoicePreviewTextView = findViewById(R.id.invoice_preview_text_view);

		// Retrieve the Invoice from the Intent
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Invoice invoice = new Invoice();
			invoice.setId(bundle.getInt("id"));
			invoice.setInvoiceNumber(bundle.getString("invoiceNumber"));
			invoice.setCustomerName(bundle.getString("customerName"));
			invoice.setCustomerContact(bundle.getString("customerContact"));
			invoice.setDate(bundle.getString("date"));
			invoice.setTotalAmount(bundle.getDouble("totalAmount"));
			invoice.setNotes(bundle.getString("notes"));

			// Retrieve the list of items
			ArrayList<Bundle> itemBundles = bundle.getParcelableArrayList("items");
			List<InvoiceItem> items = new ArrayList<>();
			if (itemBundles != null) {
				for (Bundle itemBundle : itemBundles) {
					InvoiceItem item = new InvoiceItem();
					item.setId(itemBundle.getInt("id"));
					item.setItemName(itemBundle.getString("itemName"));
					item.setQuantity(itemBundle.getInt("quantity"));
					item.setUnitPrice(itemBundle.getDouble("unitPrice"));
					item.setTotalPrice(itemBundle.getDouble("totalPrice"));
					items.add(item);
				}
			}
			invoice.setItems(items);

			displayInvoiceDetails(invoice);
		}
	}

	private void displayInvoiceDetails(Invoice invoice) {
		// Display the invoice details
		String invoiceDetails = "Invoice Number: " + invoice.getInvoiceNumber() + "\n" +
				"Customer Name: " + invoice.getCustomerName() + "\n" +
				"Customer Contact: " + invoice.getCustomerContact() + "\n" +
				"Date: " + invoice.getDate() + "\n" +
				"Total Amount: " + invoice.getTotalAmount() + "\n" +
				"Notes: " + invoice.getNotes();

		// Display each item in the invoice
		StringBuilder itemsDetails = new StringBuilder("\nItems:\n");
		for (InvoiceItem item : invoice.getItems()) {
			itemsDetails.append(" - ").append(item.getItemName()).append(" (")
					.append(item.getQuantity()).append(" @ ")
					.append(item.getUnitPrice()).append("): ")
					.append(item.getTotalPrice()).append("\n");
		}

		invoicePreviewTextView.setText(invoiceDetails + itemsDetails.toString());
	}
}
