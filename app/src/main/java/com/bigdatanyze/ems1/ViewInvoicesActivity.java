package com.bigdatanyze.ems1;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigdatanyze.ems1.adapter.InvoiceAdapter;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

		ImageView pdfIcon = findViewById(R.id.pdf_icon);
		pdfIcon.setOnClickListener(v -> generateInvoicePDF());

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

	private void generateInvoicePDF() {
		List<Invoice> invoices = invoiceViewModel.getAllInvoices().getValue();
		if (invoices == null || invoices.isEmpty()) {
			Toast.makeText(this, "No invoices available to generate PDF", Toast.LENGTH_SHORT).show();
			return;
		}

		// Create a new PDF document
		PdfDocument pdfDocument = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
		PdfDocument.Page page = pdfDocument.startPage(pageInfo);

		// Draw content on the PDF
		// Here, you would use the Canvas object from the page to draw your PDF content.
		// Example: page.getCanvas().drawText("Hello World", 10, 10, new Paint());

		pdfDocument.finishPage(page);

		// Save the PDF file
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Invoice.pdf");
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			pdfDocument.writeTo(outputStream);
			Toast.makeText(this, "PDF generated successfully", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		pdfDocument.close();
	}
}
