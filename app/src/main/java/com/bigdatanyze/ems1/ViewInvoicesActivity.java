package com.bigdatanyze.ems1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigdatanyze.ems1.adapter.InvoiceAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewInvoicesActivity extends AppCompatActivity implements InvoiceAdapter.OnInvoiceClickListener {

	private static final int REQUEST_WRITE_STORAGE = 112;

	private RecyclerView invoicesRecyclerView;
	private InvoiceAdapter invoiceAdapter;
	private InvoiceViewModel invoiceViewModel;
	private TextView totalSalesTextView;
	private TextView noInvoicesTextView;
	private List<Invoice> allInvoices = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invoices);

		totalSalesTextView = findViewById(R.id.total_sales_text_view);
		totalSalesTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark)); // Set total sales text color to green

		invoicesRecyclerView = findViewById(R.id.invoices_recycler_view);
		invoicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		noInvoicesTextView = findViewById(R.id.no_invoices_text_view);

		// Check and request storage permission for Android 12 or below
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
			} else {
				setupViewModel(); // Permission already granted
			}
		} else {
			// For Android 13+, no need to request WRITE_EXTERNAL_STORAGE permission.
			setupViewModel();
		}
	}

	private void setupViewModel() {
		// Initialize ViewModel
		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

		// Observe the LiveData from the ViewModel
		invoiceViewModel.getAllInvoices().observe(this, invoices -> {
			try {
				if (invoices != null && !invoices.isEmpty()) {
					noInvoicesTextView.setVisibility(View.GONE);
					invoicesRecyclerView.setVisibility(View.VISIBLE);

					Log.d("ViewInvoicesActivity", "Number of invoices fetched: " + invoices.size());

					allInvoices = invoices; // Keep a reference to all invoices for search filtering

					if (invoiceAdapter == null) {
						invoiceAdapter = new InvoiceAdapter(this, invoices, this);
						invoicesRecyclerView.setAdapter(invoiceAdapter);
					} else {
						invoiceAdapter.updateInvoices(invoices);
					}

					updateTotalSales(invoices);
				} else {
					showNoInvoicesMessage();
				}
			} catch (Exception e) {
				Log.e("ViewInvoicesActivity", "Error fetching invoices: " + e.getMessage(), e);
				showNoInvoicesMessage();
			}
		});
	}

	private void showNoInvoicesMessage() {
		invoicesRecyclerView.setVisibility(View.GONE);
		noInvoicesTextView.setVisibility(View.VISIBLE);
		noInvoicesTextView.setText("No Invoices to View");
		totalSalesTextView.setText("Total Sales: $0.00");
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
		if (invoice == null || isFinishing()) {
			// If the activity is finishing or invoice is null, return early
			return;
		}

		// Use app-specific directory for storing PDFs
		File pdfDir = new File(getExternalFilesDir(null), "Finverge");
		if (!pdfDir.exists()) {
			pdfDir.mkdirs(); // Create the directory if it doesn't exist
		}

		File pdfFile = new File(pdfDir, "Invoice_" + invoice.getInvoiceNumber() + ".pdf");

		if (pdfFile.exists()) {
			openGeneratedPDF(pdfFile);
		} else {
			try {
				generateInvoicePdf(invoice, pdfFile);
				openGeneratedPDF(pdfFile);
			} catch (Exception e) {
				Log.e("ViewInvoicesActivity", "Error generating PDF: " + e.getMessage(), e);
				Toast.makeText(this, "Error generating PDF.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void generateInvoicePdf(Invoice invoice, File pdfFile) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
		document.open();
		document.add(new Paragraph("Invoice"));
		document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
		document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
		document.add(new Paragraph("Total Amount: $" + invoice.getTotalAmount()));
		document.add(new Paragraph("Date: " + invoice.getDate()));
		document.close();
		Log.d("ViewInvoicesActivity", "PDF created at: " + pdfFile.getAbsolutePath());
	}

	private void openGeneratedPDF(File pdfFile) {
		Uri pdfUri = FileProvider.getUriForFile(
				this,
				getApplicationContext().getPackageName() + ".fileprovider",
				pdfFile
		);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(pdfUri, "application/pdf");
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				filterInvoices(newText);
				return true;
			}
		});
		return true;
	}

	private void filterInvoices(String query) {
		List<Invoice> filteredInvoices = new ArrayList<>();
		for (Invoice invoice : allInvoices) {
			if (invoice.getInvoiceNumber().toLowerCase().contains(query.toLowerCase()) ||
					invoice.getCustomerName().toLowerCase().contains(query.toLowerCase())) {
				filteredInvoices.add(invoice);
			}
		}
		invoiceAdapter.updateInvoices(filteredInvoices);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_WRITE_STORAGE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				setupViewModel();
			} else {
				Toast.makeText(this, "Storage permission is required to generate PDFs.", Toast.LENGTH_SHORT).show();
				noInvoicesTextView.setVisibility(View.VISIBLE);
				noInvoicesTextView.setText("Enable storage permission to view invoices.");
			}
		}
	}
}
