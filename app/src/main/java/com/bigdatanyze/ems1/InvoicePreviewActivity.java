package com.bigdatanyze.ems1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigdatanyze.ems1.adapter.InvoiceItemAdapter;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.model.InvoiceItem;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class InvoicePreviewActivity extends AppCompatActivity {


	private static final int STORAGE_PERMISSION_CODE = 100;
	private TextView invoiceNumberTextView, invoiceDateTextView, customerNameTextView, customerContactTextView;
	private TextView totalAmountTextView, additionalNotesTextView, businessNameTextView;
	private RecyclerView itemRecyclerView;
	private Button downloadPdfButton, openPdfButton, sharePdfButton;
	private InvoiceItemAdapter invoiceItemAdapter;
	private List<InvoiceItem> invoiceItemList;
	private BusinessProfileViewModel businessProfileViewModel;
	private String pdfFilePath; // To store the path of the generated PDF
	private String businessName = "Finverge Pvt Ltd";  // Default demo name
	private String businessAddress = "Company Address";  // Default demo address
	private String businessCityStateZip = "City, State, ZIP";  // Default demo city, state, ZIP
	private String businessEmail = "example@company.com";  // Default demo email
	private String businessPhone = "Phone: (123) 456-7890";  // Default demo phone number


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
		businessNameTextView = findViewById(R.id.business_name);  // Assuming this is for business profile name
		itemRecyclerView = findViewById(R.id.item_recycler_view);
		downloadPdfButton = findViewById(R.id.download_pdf_button);
		openPdfButton = findViewById(R.id.open_pdf_button);
		sharePdfButton = findViewById(R.id.share_pdf_button);

		// Set up RecyclerView
		invoiceItemList = new ArrayList<>();
		invoiceItemAdapter = new InvoiceItemAdapter(invoiceItemList);
		itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		itemRecyclerView.setAdapter(invoiceItemAdapter);

		// Check for storage permissions
		checkStoragePermissions();

		// Retrieve and display invoice data
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			invoiceNumberTextView.setText("Invoice Number: " + extras.getString("invoiceNumber"));
			invoiceDateTextView.setText("Date: " + extras.getString("date"));
			customerNameTextView.setText("Customer: " + extras.getString("customerName"));
			customerContactTextView.setText("Contact: " + extras.getString("customerContact"));
			totalAmountTextView.setText(String.format("Total Amount: %.2f", extras.getDouble("totalAmount")));
			additionalNotesTextView.setText("Notes: " + extras.getString("notes"));

			ArrayList<Bundle> itemBundles = extras.getParcelableArrayList("items");
			if (itemBundles != null) {
				for (Bundle itemBundle : itemBundles) {
					String itemName = itemBundle.getString("itemName");
					int quantity = itemBundle.getInt("quantity");
					double unitPrice = itemBundle.getDouble("unitPrice");
					double totalPrice = itemBundle.getDouble("totalPrice");

					InvoiceItem invoiceItem = new InvoiceItem(itemName, quantity, unitPrice, totalPrice);
					invoiceItemList.add(invoiceItem);
				}
				invoiceItemAdapter.notifyDataSetChanged();
			}
		}

		// Set up BusinessProfileViewModel
		businessProfileViewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);
		businessProfileViewModel.getBusinessProfile().observe(this, new Observer<BusinessProfile>() {
			@Override
			public void onChanged(BusinessProfile businessProfile) {
				if (businessProfile != null) {
					businessName = businessProfile.getBusinessName();
					businessAddress = businessProfile.getCompanyAddress();
					businessCityStateZip = businessProfile.getCityStateZip();
					businessEmail = businessProfile.getEmail();
					businessPhone = "Phone: " + businessProfile.getPhoneNumber();
					businessNameTextView.setText(businessProfile.getBusinessName());  // Update with business name				}
			}}
		});

		// Set up button listeners
		downloadPdfButton.setOnClickListener(v -> generatePdf());
		openPdfButton.setOnClickListener(v -> openPdf());
		sharePdfButton.setOnClickListener(v -> sharePdf());
	}
	/**
	 * Checks and requests storage permissions if not already granted.
	 */
	private void checkStoragePermissions() {
		// For Android 6.0 and above
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

			// Request permissions
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
					STORAGE_PERMISSION_CODE);
		}
	}


	/**
	 * Generates the PDF of the invoice with improved layout and design.
	 */
	private void generatePdf() {
		// Initialize PDF document
		PdfDocument pdfDocument = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(600, 900, 1).create();
		PdfDocument.Page page = pdfDocument.startPage(pageInfo);

		// Canvas and Paint objects
		Paint paint = new Paint();
		Paint titlePaint = new Paint();
		titlePaint.setTextSize(16);
		titlePaint.setFakeBoldText(true);

		Canvas canvas = page.getCanvas();

		// Set margins
		int marginLeft = 20;
		int marginRight = 580;
		int yPosition = 25; // Top margin

		// Draw "Tax Invoice" heading, centered
		Paint taxInvoicePaint = new Paint();
		taxInvoicePaint.setTextSize(18);
		taxInvoicePaint.setFakeBoldText(true);
		taxInvoicePaint.setTextAlign(Paint.Align.CENTER);

		// Center the text
		float canvasWidth = canvas.getWidth();
		canvas.drawText("Tax Invoice", canvasWidth / 2, yPosition, taxInvoicePaint);
		yPosition += 30;

		// Draw business profile or default demo details
		canvas.drawText(businessName, marginLeft, yPosition, titlePaint);  // Use dynamic business name
		yPosition += 20;
		canvas.drawText(businessAddress, marginLeft, yPosition, paint);  // Use dynamic address
		yPosition += 20;
		canvas.drawText(businessCityStateZip, marginLeft, yPosition, paint);  // Use dynamic city, state, ZIP
		yPosition += 20;
		canvas.drawText(businessEmail, marginLeft, yPosition, paint);  // Use dynamic email
		yPosition += 20;
		canvas.drawText(businessPhone, marginLeft, yPosition, paint);  // Use dynamic phone
		yPosition += 25;

		// Draw horizontal line under header
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(marginLeft, yPosition, marginRight, yPosition, paint);
		paint.setStyle(Paint.Style.FILL);
		yPosition += 20;

		// Billing details header
		titlePaint.setTextSize(14);
		canvas.drawText("Billing Details", marginLeft, yPosition, titlePaint);
		yPosition += 20;

		// Draw invoice details
		paint.setTextSize(12);
		canvas.drawText("Invoice Number: " + invoiceNumberTextView.getText().toString().replace("Invoice Number: ", ""), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Date: " + invoiceDateTextView.getText().toString().replace("Date: ", ""), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Customer: " + customerNameTextView.getText().toString().replace("Customer: ", ""), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Contact: " + customerContactTextView.getText().toString().replace("Contact: ", ""), marginLeft, yPosition, paint);
		yPosition += 25;

		// Shipping address header
		canvas.drawText("Shipping Address", marginLeft, yPosition, titlePaint);
		yPosition += 20;
		canvas.drawText("123 Shipping Street", marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("City, State, ZIP", marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Phone: (987) 654-3210", marginLeft, yPosition, paint);
		yPosition += 30;

		// Item list header
		titlePaint.setTextSize(14);
		canvas.drawText("Item Name", marginLeft, yPosition, titlePaint);
		canvas.drawText("Quantity", 200, yPosition, titlePaint);
		canvas.drawText("Unit Price", 300, yPosition, titlePaint);
		canvas.drawText("Amount", 400, yPosition, titlePaint);
		yPosition += 15;

		// Draw a horizontal line under the item list header
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(marginLeft, yPosition, marginRight, yPosition, paint);
		paint.setStyle(Paint.Style.FILL);
		yPosition += 20;

		// Draw the items
		for (InvoiceItem item : invoiceItemList) {
			canvas.drawText(item.getItemName(), marginLeft, yPosition, paint);
			canvas.drawText(String.valueOf(item.getQuantity()), 200, yPosition, paint);
			canvas.drawText(String.format("%.2f", item.getUnitPrice()), 300, yPosition, paint);
			canvas.drawText(String.format("%.2f", item.getTotalPrice()), 400, yPosition, paint);
			yPosition += 20;
		}

		// Draw total amount and additional notes
		yPosition += 30;
		canvas.drawText("Total Amount: " + totalAmountTextView.getText().toString().replace("Total Amount: ", ""), marginLeft, yPosition, titlePaint);
		yPosition += 20;
		canvas.drawText(additionalNotesTextView.getText().toString(), marginLeft, yPosition, paint);
		yPosition += 40;

		// Draw terms and conditions
		titlePaint.setTextSize(12);
		canvas.drawText("Terms and Conditions", marginLeft, yPosition, titlePaint);
		yPosition += 20;
		paint.setTextSize(10);
		canvas.drawText("1. Payment is due within 30 days.", marginLeft, yPosition, paint);
		yPosition += 15;
		canvas.drawText("2. Late payments may incur additional charges.", marginLeft, yPosition, paint);
		yPosition += 15;
		canvas.drawText("3. Goods sold are not refundable.", marginLeft, yPosition, paint);
		yPosition += 20;

		// Finish the page
		pdfDocument.finishPage(page);

		// Save the PDF in the Finverge folder in Documents
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Finverge");
		if (!directory.exists()) {
			boolean mkdirs = directory.mkdirs(); // Create the directory if it doesn't exist
			if (!mkdirs) {
				Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
				pdfDocument.close();
				return;
			}
		}

		// Define the file path inside the Finverge folder
		String fileName = "Invoice_" + invoiceNumberTextView.getText().toString().replace("Invoice Number: ", "") + ".pdf";
		File file = new File(directory, fileName);
		pdfFilePath = file.getAbsolutePath();

		try (OutputStream outputStream = new FileOutputStream(file)) {
			// Write PDF content to file
			pdfDocument.writeTo(outputStream);
			Toast.makeText(this, "PDF saved to: " + filePathFormatted(file.getAbsolutePath()), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// Handle exception
			Toast.makeText(this, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			pdfDocument.close(); // Close the PDF document
		}
	}

	/**
	 * Formats the file path to be more user-friendly.
	 *
	 * @param path The absolute file path.
	 * @return Formatted file path string.
	 */
	private String filePathFormatted(String path) {
		// Replace the absolute path with a more readable format
		// For example: /storage/emulated/0/Documents/Finverge/Invoice_12345.pdf
		// can be shown as Documents/Finverge/Invoice_12345.pdf
		File file = new File(path);
		String[] parts = file.getAbsolutePath().split("/Documents/");
		if (parts.length > 1) {
			return "Documents/" + parts[1];
		} else {
			return path;
		}
	}

	/**
	 * Opens the generated PDF using an external PDF viewer.
	 */
	private void openPdf() {
		if (pdfFilePath != null) {
			File file = new File(pdfFilePath);
			if (file.exists()) {
				Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(pdfUri, "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

				try {
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(this, "No PDF viewer found. Please install a PDF viewer to open the file.", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			} else {
				Toast.makeText(this, "PDF file does not exist. Please generate the PDF first.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Download the PDF first.", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Shares the generated PDF via available apps (e.g., Email, Messaging).
	 */
	private void sharePdf() {
		if (pdfFilePath != null) {
			File file = new File(pdfFilePath);
			if (file.exists()) {
				Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("application/pdf");
				shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
				shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

				try {
					startActivity(Intent.createChooser(shareIntent, "Share PDF via"));
				} catch (Exception e) {
					Toast.makeText(this, "No suitable app found to share the PDF.", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			} else {
				Toast.makeText(this, "PDF file does not exist. Please generate the PDF first.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Generate the PDF first.", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Handles the result of permission requests.
	 *
	 * @param requestCode  The request code passed in requestPermissions().
	 * @param permissions  The requested permissions.
	 * @param grantResults The grant results for the corresponding permissions.
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
	                                       @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == STORAGE_PERMISSION_CODE) {
			boolean allGranted = true;
			if (grantResults.length > 0) {
				for (int result : grantResults) {
					if (result != PackageManager.PERMISSION_GRANTED) {
						allGranted = false;
						break;
					}
				}
			} else {
				allGranted = false;
			}

			if (allGranted) {
				Toast.makeText(this, "Storage permissions granted.", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Storage permissions denied. The app may not function correctly.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
