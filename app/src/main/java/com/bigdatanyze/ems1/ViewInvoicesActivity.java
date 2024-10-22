package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
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
	private TextView totalSalesTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invoices);

		totalSalesTextView = findViewById(R.id.total_sales_text_view);
		invoicesRecyclerView = findViewById(R.id.invoices_recycler_view);
		invoicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Observe the LiveData from the ViewModel
		invoiceViewModel.getAllInvoices().observe(this, this::updateInvoiceList);
	}

	private void updateInvoiceList(List<Invoice> invoices) {
		if (invoices != null && !invoices.isEmpty()) {
			if (invoiceAdapter == null) {
				invoiceAdapter = new InvoiceAdapter(this, invoices, this);
				invoicesRecyclerView.setAdapter(invoiceAdapter);
			} else {
				invoiceAdapter.updateInvoices(invoices);
			}
			updateTotalSales(invoices);
		} else {
			// Handle the case when there are no invoices
			Toast.makeText(this, "No invoices found", Toast.LENGTH_SHORT).show();
			totalSalesTextView.setText("Total Sales: $0.00");
		}
	}

	private void updateTotalSales(List<Invoice> invoices) {
		double totalSales = 0.0;
		for (Invoice invoice : invoices) {
			totalSales += invoice.getTotalAmount();
		}
		totalSalesTextView.setText(String.format("Total Sales: $%.2f", totalSales));
	}

	@Override
	public void onInvoiceClick(Invoice invoice) {
		Toast.makeText(this, "Invoice Clicked: " + invoice.getInvoiceNumber(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, PDFViewerActivity.class);
		intent.putExtra("invoiceId", invoice.getId());
		startActivity(intent);
	}
}
