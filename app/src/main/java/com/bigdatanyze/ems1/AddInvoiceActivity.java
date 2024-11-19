package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bigdatanyze.ems1.R;

public class AddInvoiceActivity extends AppCompatActivity {

	private TextView invoiceNumberTextView;
	private TextView invoiceDateTextView;
	private TextView dueDateTextView;
	private Button addItemButton;
	private Button saveInvoiceButton;

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_invoice);

		// Find views by ID
		invoiceNumberTextView = findViewById(R.id.invoiceNumberTextView);
		invoiceDateTextView = findViewById(R.id.invoiceDateTextView);
		dueDateTextView = findViewById(R.id.dueDateTextView);
		addItemButton = findViewById(R.id.addItemButton);
		saveInvoiceButton = findViewById(R.id.saveInvoiceButton);

		// Button to handle adding new item
		addItemButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Example logic: just show a Toast for now
				Toast.makeText(AddInvoiceActivity.this, "Item added to invoice", Toast.LENGTH_SHORT).show();
				// You can dynamically add new views for each item here, e.g., using RecyclerView.
			}
		});

		// Button to handle saving the invoice
		saveInvoiceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveInvoice();
			}
		});
	}

	private void saveInvoice() {
		String invoiceNumber = invoiceNumberTextView.getText().toString();
		String invoiceDate = invoiceDateTextView.getText().toString();
		String dueDate = dueDateTextView.getText().toString();

		// Add your logic to save the invoice data to a database or send it to another activity
		// Example logic: show a Toast with invoice data
		String message = "Invoice Saved! \n" +
				"Invoice #: " + invoiceNumber + "\n" +
				"Date: " + invoiceDate + "\n" +
				"Due Date: " + dueDate;

		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
