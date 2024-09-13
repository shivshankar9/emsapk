package com.bigdatanyze.ems1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.InvoiceAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;

import java.util.List;

public class ViewInvoicesActivity extends AppCompatActivity {

	private RecyclerView invoicesRecyclerView;
	private InvoiceAdapter invoiceAdapter;
	private InvoiceViewModel invoiceViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invoices);

		invoicesRecyclerView = findViewById(R.id.invoices_recycler_view);
		invoicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Observe the list of invoices and set them to the adapter
		invoiceViewModel.getAllInvoices().observe(this, invoices -> {
			if (invoices != null) {
				invoiceAdapter = new InvoiceAdapter(invoices, this::editInvoice);
				invoicesRecyclerView.setAdapter(invoiceAdapter);
			}
		});
	}

	// Method to edit invoice
	private void editInvoice(Invoice invoice) {
		Intent intent = new Intent(ViewInvoicesActivity.this, AddInvoiceActivity.class);
		intent.putExtra("invoice_id", invoice.getId()); // Pass the invoice ID to edit
		startActivity(intent);
	}
}
