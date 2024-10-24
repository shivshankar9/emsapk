package com.bigdatanyze.ems1.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.InvoiceItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfUtils {
	public static void generateInvoicePdf(Context context, Invoice invoice) {

		// Initialize PDF document
		PdfDocument pdfDocument = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(600, 900, 1).create();
		PdfDocument.Page page = pdfDocument.startPage(pageInfo);

		// Canvas and Paint objects
		Canvas canvas = page.getCanvas();
		Paint paint = new Paint();
		Paint titlePaint = new Paint();
		titlePaint.setTextSize(16);
		titlePaint.setFakeBoldText(true);


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

		// Draw business profile details
		canvas.drawText(invoice.getBusinessName(), marginLeft, yPosition, titlePaint);
		yPosition += 20;
		canvas.drawText(invoice.getCompanyAddress(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText(invoice.getCityStateZip(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText(invoice.getEmail(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText(invoice.getPhoneNumber(), marginLeft, yPosition, paint);
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
		canvas.drawText("Invoice Number: " + invoice.getInvoiceNumber(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Date: " + invoice.getDate(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Customer: " + invoice.getCustomerName(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Contact: " + invoice.getCustomerContact(), marginLeft, yPosition, paint);
		yPosition += 25;

		// Shipping address header
		canvas.drawText("Shipping Address", marginLeft, yPosition, titlePaint);
		yPosition += 20;
		canvas.drawText("123 Shipping Street", marginLeft, yPosition, paint); // Replace with dynamic shipping address if needed
		yPosition += 20;
		canvas.drawText("City, State, ZIP", marginLeft, yPosition, paint); // Replace with dynamic shipping city, state, ZIP
		yPosition += 20;
		canvas.drawText("Phone: (987) 654-3210", marginLeft, yPosition, paint); // Replace with dynamic shipping phone if needed
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
		for (InvoiceItem item : invoice.getItems()) {
			canvas.drawText(item.getItemName(), marginLeft, yPosition, paint);
			canvas.drawText(String.valueOf(item.getQuantity()), 200, yPosition, paint);
			canvas.drawText(String.format("%.2f", item.getUnitPrice()), 300, yPosition, paint);
			canvas.drawText(String.format("%.2f", item.getTotalPrice()), 400, yPosition, paint);
			yPosition += 20;
		}

		// Draw total amount and additional notes
		yPosition += 30;
		canvas.drawText("Total Amount: " + String.format("%.2f", invoice.getTotalAmount()), marginLeft, yPosition, titlePaint);
		yPosition += 20;
		canvas.drawText(invoice.getNotes(), marginLeft, yPosition, paint);
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
				Toast.makeText(context, "Failed to create directory", Toast.LENGTH_SHORT).show();
				pdfDocument.close();
				return;
			}
		}

		// Define the file path inside the Finverge folder
		String fileName = "Invoice_" + invoice.getInvoiceNumber() + ".pdf";
		File file = new File(directory, fileName);
		String filePath = file.getAbsolutePath();

		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			// Write PDF content to file
			pdfDocument.writeTo(outputStream);
			Toast.makeText(context, "PDF saved to: " + filePathFormatted(file.getAbsolutePath()), Toast.LENGTH_LONG).show();

			// Open the PDF after saving
			openPdf(context, filePath);
		} catch (IOException e) {
			// Handle exception
			Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			pdfDocument.close(); // Close the PDF document
		}
	}

	private static void openPdf(Context context, String filePath) {
		File pdfFile = new File(filePath);
		Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", pdfFile);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(pdfUri, "application/pdf");
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		try {
			context.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(context, "No application available to view PDF", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Formats the file path to be more user-friendly.
	 *
	 * @param path The absolute file path.
	 * @return Formatted file path string.
	 */
	private static String filePathFormatted(String path) {
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
}
