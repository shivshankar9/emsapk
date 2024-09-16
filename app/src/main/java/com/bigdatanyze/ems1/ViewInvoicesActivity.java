package com.bigdatanyze.ems1;

import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invoices);

		invoicesRecyclerView = findViewById(R.id.invoices_recycler_view);
		invoicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		invoiceViewModel.getAllInvoices().observe(this, invoices -> {
			if (invoices != null) {
				invoiceAdapter = new InvoiceAdapter(this, invoices, this); // Pass context and listener
				invoicesRecyclerView.setAdapter(invoiceAdapter);
			}
		});
	}

	@Override
	public void onInvoiceClick(Invoice invoice) {
		// Handle the invoice click event
		Toast.makeText(this, "Invoice Clicked: " + invoice.getInvoiceNumber(), Toast.LENGTH_SHORT).show();
		// Implement PDF generation or other functionality here
	}
}
