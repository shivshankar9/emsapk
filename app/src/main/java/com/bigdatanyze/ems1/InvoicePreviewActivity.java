package com.bigdatanyze.ems1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class InvoicePreviewActivity extends AppCompatActivity {

	private static final int STORAGE_PERMISSION_CODE = 100;
	private TextView invoicePreviewTextView;
	private Button downloadPdfButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_preview);

		invoicePreviewTextView = findViewById(R.id.invoice_preview_text_view);
		downloadPdfButton = findViewById(R.id.download_pdf_button);

		// Display invoice details
		displayInvoiceDetails();

		// Check for storage permission and set button listener
		downloadPdfButton.setOnClickListener(v -> {
			if (checkStoragePermission()) {
				downloadPDF();
			} else {
				requestStoragePermission();
			}
		});
	}

	private void displayInvoiceDetails() {
		// Get data from the intent
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String invoiceNumber = extras.getString("invoiceNumber");
			String customerName = extras.getString("customerName");
			String customerContact = extras.getString("customerContact");
			String date = extras.getString("date");
			double totalAmount = extras.getDouble("totalAmount");
			String notes = extras.getString("notes");
			ArrayList<Bundle> itemBundles = extras.getParcelableArrayList("items");

			// Build invoice details string
			StringBuilder invoiceDetails = new StringBuilder();
			invoiceDetails.append("Invoice Number: ").append(invoiceNumber).append("\n");
			invoiceDetails.append("Customer Name: ").append(customerName).append("\n");
			invoiceDetails.append("Customer Contact: ").append(customerContact).append("\n");
			invoiceDetails.append("Date: ").append(date).append("\n");
			invoiceDetails.append("Total Amount: $").append(totalAmount).append("\n");
			invoiceDetails.append("Notes: ").append(notes).append("\n\n");

			invoiceDetails.append("Items:\n");
			if (itemBundles != null) {
				for (Bundle itemBundle : itemBundles) {
					String itemName = itemBundle.getString("itemName");
					int quantity = itemBundle.getInt("quantity");
					double unitPrice = itemBundle.getDouble("unitPrice");
					double totalPrice = itemBundle.getDouble("totalPrice");
					invoiceDetails.append(String.format("%s - Quantity: %d, Unit Price: $%.2f, Total Price: $%.2f\n",
							itemName, quantity, unitPrice, totalPrice));
				}
			}

			invoicePreviewTextView.setText(invoiceDetails.toString());
		}
	}

	private boolean checkStoragePermission() {
		return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
	}

	private void requestStoragePermission() {
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
	}

	private void downloadPDF() {
		PdfDocument pdfDocument = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
		PdfDocument.Page page = pdfDocument.startPage(pageInfo);

		// Drawing content on the PDF
		android.graphics.Paint paint = new android.graphics.Paint();
		String invoiceDetails = invoicePreviewTextView.getText().toString();
		page.getCanvas().drawText(invoiceDetails, 80, 100, paint);
		pdfDocument.finishPage(page);

		// Save PDF to file
		File file = new File(Environment.getExternalStorageDirectory(), "InvoicePreview.pdf");
		try {
			pdfDocument.writeTo(new FileOutputStream(file));
			Toast.makeText(this, "PDF downloaded successfully: " + file.getPath(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
		} finally {
			pdfDocument.close();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == STORAGE_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				downloadPDF();
			} else {
				Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
