package com.bigdatanyze.ems1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;
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
	private TextView invoiceNumberTextView, invoiceDateTextView, customerNameTextView, customerContactTextView;
	private TextView totalAmountTextView, additionalNotesTextView;
	private LinearLayout itemListLayout;
	private Button downloadPdfButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_preview);

		// Initialize views
		invoiceNumberTextView = findViewById(R.id.invoice_number);
		invoiceDateTextView = findViewById(R.id.invoice_date);
		customerNameTextView = findViewById(R.id.customer_name);
		customerContactTextView = findViewById(R.id.customer_contact);
		totalAmountTextView = findViewById(R.id.total_amount);
		additionalNotesTextView = findViewById(R.id.additional_notes);
		itemListLayout = findViewById(R.id.item_list);
		downloadPdfButton = findViewById(R.id.download_pdf_button);

		// Check for storage permissions
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
		}

		// Retrieve and display invoice data
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			invoiceNumberTextView.setText(extras.getString("invoiceNumber"));
			invoiceDateTextView.setText(extras.getString("date"));
			customerNameTextView.setText(extras.getString("customerName"));
			customerContactTextView.setText(extras.getString("customerContact"));
			totalAmountTextView.setText(String.format("Total Amount: %.2f", extras.getDouble("totalAmount")));
			additionalNotesTextView.setText(extras.getString("notes"));

			ArrayList<Bundle> itemBundles = extras.getParcelableArrayList("items");
			if (itemBundles != null) {
				for (Bundle itemBundle : itemBundles) {
					TextView itemView = new TextView(this);
					itemView.setText(String.format("%s - Quantity: %d, Unit Price: %.2f, Total Price: %.2f",
							itemBundle.getString("itemName"),
							itemBundle.getInt("quantity"),
							itemBundle.getDouble("unitPrice"),
							itemBundle.getDouble("totalPrice")));
					itemListLayout.addView(itemView);
				}
			}
		}

		// Set up PDF generation button
		downloadPdfButton.setOnClickListener(v -> generatePdf());
	}

	// Method to generate the PDF of the invoice
	private void generatePdf() {
		// Set up the PDF document and page
		PdfDocument pdfDocument = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(600, 900, 1).create();
		PdfDocument.Page page = pdfDocument.startPage(pageInfo);

		// Set up the paint styles
		Paint paint = new Paint();
		paint.setTextSize(12);
		Paint titlePaint = new Paint();
		titlePaint.setTextSize(16);
		titlePaint.setFakeBoldText(true);

		// Draw the company header
		page.getCanvas().drawText("Finverge Pvt Ltd", 10, 25, titlePaint);
		page.getCanvas().drawText("Company Address", 10, 45, paint);
		page.getCanvas().drawText("City, State, ZIP", 10, 65, paint);
		page.getCanvas().drawText("Email: example@company.com", 10, 85, paint);
		page.getCanvas().drawText("Phone: (123) 456-7890", 10, 105, paint);

		// Draw invoice details
		page.getCanvas().drawText("Invoice Number: " + invoiceNumberTextView.getText().toString(), 10, 145, paint);
		page.getCanvas().drawText("Date: " + invoiceDateTextView.getText().toString(), 10, 165, paint);
		page.getCanvas().drawText("Customer: " + customerNameTextView.getText().toString(), 10, 185, paint);
		page.getCanvas().drawText("Contact: " + customerContactTextView.getText().toString(), 10, 205, paint);

		// Draw the item list header
		int yPosition = 245;
		titlePaint.setTextSize(14);
		page.getCanvas().drawText("Item Name", 10, yPosition, titlePaint);
		page.getCanvas().drawText("Quantity", 150, yPosition, titlePaint);
		page.getCanvas().drawText("Unit Price", 250, yPosition, titlePaint);
		page.getCanvas().drawText("Total Price", 350, yPosition, titlePaint);

		// Draw the items
		yPosition += 20;
		for (int i = 0; i < itemListLayout.getChildCount(); i++) {
			TextView itemView = (TextView) itemListLayout.getChildAt(i);
			String[] itemDetails = itemView.getText().toString().split(", ");

			// Split item details into appropriate fields
			String itemName = itemDetails[0];
			String quantity = itemDetails[1].replace("Quantity: ", "");
			String unitPrice = itemDetails[2].replace("Unit Price: ", "");
			String totalPrice = itemDetails[3].replace("Total Price: ", "");

			page.getCanvas().drawText(itemName, 10, yPosition, paint);
			page.getCanvas().drawText(quantity, 150, yPosition, paint);
			page.getCanvas().drawText(unitPrice, 250, yPosition, paint);
			page.getCanvas().drawText(totalPrice, 350, yPosition, paint);

			yPosition += 20;
		}

		// Draw the total amount and notes
		yPosition += 30;
		page.getCanvas().drawText("Total Amount: " + totalAmountTextView.getText().toString(), 10, yPosition, titlePaint);
		yPosition += 20;
		page.getCanvas().drawText("Notes: " + additionalNotesTextView.getText().toString(), 10, yPosition, paint);

		pdfDocument.finishPage(page);

		// Save the PDF in the Finverge folder in Documents
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Finverge");
		if (!directory.exists()) {
			boolean mkdirs = directory.mkdirs(); // Create the directory if it doesn't exist
			if (!mkdirs) {
				Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
				return;
			}
		}

		// Define the file path inside the Finverge folder
		String filePath = directory.getAbsolutePath() + "/Invoice_" + invoiceNumberTextView.getText().toString() + ".pdf";

		try (OutputStream outputStream = new FileOutputStream(filePath)) {
			// Write PDF content to file
			pdfDocument.writeTo(outputStream);
			Toast.makeText(this, "PDF saved to: " + filePath, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// Handle exception
			Toast.makeText(this, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			pdfDocument.close(); // Close the PDF document
		}
	}

	// Handle permission request result
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == STORAGE_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
