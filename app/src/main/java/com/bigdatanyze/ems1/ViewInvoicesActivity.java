package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.InvoiceAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;
import java.util.List;

public class ViewInvoicesActivity extends AppCompatActivity implements InvoiceAdapter.OnInvoiceClickListener {

	private RecyclerView invoicesRecyclerView;
	private InvoiceAdapter invoiceAdapter;
	private InvoiceViewModel invoiceViewModel;
	private TextView totalSalesTextView; // TextView for total sales
	private TextView noInvoicesTextView; // TextView to show 'No invoices found'

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invoices);

		totalSalesTextView = findViewById(R.id.total_sales_text_view);
		invoicesRecyclerView = findViewById(R.id.invoices_recycler_view);
		invoicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		noInvoicesTextView = findViewById(R.id.no_invoices_text_view); // Add this TextView in your XML layout file

		// Initialize ViewModel
		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Observe the LiveData from the ViewModel
		invoiceViewModel.getAllInvoices().observe(this, invoices -> {
			try {
				if (invoices != null && !invoices.isEmpty()) {
					// Hide "No invoices found" message
					noInvoicesTextView.setVisibility(View.GONE);
					invoicesRecyclerView.setVisibility(View.VISIBLE);

					Log.d("ViewInvoicesActivity", "Number of invoices fetched: " + invoices.size());

					if (invoiceAdapter == null) {
						invoiceAdapter = new InvoiceAdapter(this, invoices, this);
						invoicesRecyclerView.setAdapter(invoiceAdapter);
					} else {
						invoiceAdapter.updateInvoices(invoices);
					}

					updateTotalSales(invoices);
				} else {
					// If no invoices are found
					showNoInvoicesMessage();
				}
			} catch (Exception e) {
				Log.e("ViewInvoicesActivity", "Error while fetching invoices: " + e.getMessage(), e);
				showNoInvoicesMessage();
			}
		});
	}

	// Show "No Invoices to View" when no invoices are available
	private void showNoInvoicesMessage() {
		invoicesRecyclerView.setVisibility(View.GONE); // Hide the RecyclerView
		noInvoicesTextView.setVisibility(View.VISIBLE); // Show 'No invoices found' message
		noInvoicesTextView.setText("No Invoices to View");
		totalSalesTextView.setText("Total Sales: $0.00"); // Reset total sales to zero
	}

	// Update total sales if invoices are available
	private void updateTotalSales(List<Invoice> invoices) {
		double totalSales = 0.0;
		for (Invoice invoice : invoices) {
			totalSales += invoice.getTotalAmount();
		}
		totalSalesTextView.setText(String.format("Total Sales: $%.2f", totalSales));
	}

	// Handle invoice click events
	@Override
	public void onInvoiceClick(Invoice invoice) {
		try {
			Toast.makeText(this, "Invoice Clicked: " + invoice.getInvoiceNumber(), Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(this, PDFViewerActivity.class);
			intent.putExtra("invoiceId", invoice.getId());
			startActivity(intent);
		} catch (Exception e) {
			Log.e("ViewInvoicesActivity", "Error opening invoice: " + e.getMessage(), e);
			Toast.makeText(this, "Error opening invoice.", Toast.LENGTH_SHORT).show();
		}
	}
}
