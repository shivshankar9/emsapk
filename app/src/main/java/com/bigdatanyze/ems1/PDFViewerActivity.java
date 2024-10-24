package com.bigdatanyze.ems1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;
import com.bigdatanyze.ems1.viewmodel.InvoiceViewModel;
import java.io.File;
import java.io.FileOutputStream;

public class PDFViewerActivity extends AppCompatActivity {

	private InvoiceViewModel invoiceViewModel;
	private BusinessProfileViewModel businessProfileViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// No need to call setContentView since we won't use any UI layout

		// Retrieve invoice ID passed from ViewInvoicesActivity
		Intent intent = getIntent();
		int invoiceId = intent.getIntExtra("invoiceId", -1);

		// Initialize ViewModels
		invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
		businessProfileViewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Fetch invoice and business profile details, then generate and open the PDF
		invoiceViewModel.getInvoiceById(invoiceId).observe(this, invoice -> {
			if (invoice != null) {
				// Fetch business profile details
				businessProfileViewModel.getBusinessProfile().observe(this, businessProfile -> {
					if (businessProfile != null) {
						try {
							// Generate the PDF
							File pdfFile = generatePDF(invoice, businessProfile);

							// Open the PDF directly
							openPDF(pdfFile);
							finish(); // Finish the activity after opening the PDF
						} catch (Exception e) {
							Log.e("PDFViewerActivity", "Error generating PDF: ", e);
						}
					} else {
						Log.e("PDFViewerActivity", "Business Profile is null.");
					}
				});
			} else {
				Log.e("PDFViewerActivity", "Invoice is null.");
			}
		});
	}

	private File generatePDF(Invoice invoice, BusinessProfile businessProfile) throws Exception {
		// Create the PDF file
		File pdfFile = new File(getExternalFilesDir(null), "invoice_" + invoice.getInvoiceNumber() + ".pdf");
		try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
			// Example content for PDF
			String pdfContent = "Invoice Number: " + invoice.getInvoiceNumber() + "\n" +
					"Customer Name: " + invoice.getCustomerName() + "\n" +
					"Total Amount: $" + invoice.getTotalAmount() + "\n\n" +
					"Business Name: " + businessProfile.getBusinessName() + "\n" +
					"Business Address: " + businessProfile.getCompanyAddress();

			fos.write(pdfContent.getBytes());
			fos.flush(); // Ensure all data is written
		}

		// Check if file is created and has content
		if (pdfFile.exists() && pdfFile.length() > 0) {
			return pdfFile;
		} else {
			throw new Exception("Failed to create PDF file.");
		}
	}

	private void openPDF(File pdfFile) {
		// Get the URI for the file using FileProvider
		Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", pdfFile);

		// Create an intent to view the PDF
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(pdfUri, "application/pdf");
		intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant temporary read permission

		// Check if there is an app to handle the PDF file
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent); // Open PDF
		} else {
			Log.e("PDFViewerActivity", "No app available to open PDF.");
		}
	}
}
