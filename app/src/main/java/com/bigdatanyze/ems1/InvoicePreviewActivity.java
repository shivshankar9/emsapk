package com.bigdatanyze.ems1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.io.OutputStream;
import java.util.ArrayList;

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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			// Android 10 and above do not need WRITE_EXTERNAL_STORAGE permission
			return true;
		}
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
		android.graphics.Canvas canvas = page.getCanvas();
		android.graphics.Paint paint = new android.graphics.Paint();
		paint.setTextSize(12f);
		paint.setColor(android.graphics.Color.BLACK);

		String invoiceDetails = invoicePreviewTextView.getText().toString();
		String[] lines = invoiceDetails.split("\n");
		float y = 100; // Starting Y coordinate
		float lineHeight = paint.getTextSize() + 10; // Line height

		for (String line : lines) {
			canvas.drawText(line, 80, y, paint);
			y += lineHeight;
		}

		pdfDocument.finishPage(page);

		// Save PDF based on Android version
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			savePDFInScopedStorage(pdfDocument);
		} else {
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
	}

	// Save the PDF using Scoped Storage (for Android 10 and above)
	private void savePDFInScopedStorage(PdfDocument pdfDocument) {
		ContentResolver resolver = getContentResolver();
		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "InvoicePreview.pdf");
		contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
		contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/Finverge");

		Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues);

		try {
			OutputStream outputStream = resolver.openOutputStream(uri);
			if (outputStream != null) {
				pdfDocument.writeTo(outputStream);
				outputStream.close();
				Toast.makeText(this, "PDF saved in Documents/YourAppFolder", Toast.LENGTH_SHORT).show();
			}
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
