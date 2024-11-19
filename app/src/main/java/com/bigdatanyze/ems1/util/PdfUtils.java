package com.bigdatanyze.ems1.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.util.Log;
import android.widget.Toast;

import com.bigdatanyze.ems1.model.Invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfUtils {
	// Default demo business details; these should be dynamically updated if needed
	private static String businessName = "Finverge Pvt Ltd";
	private static String businessAddress = "Company Address";
	private static String businessCityStateZip = "City, State, ZIP";
	private static String businessEmail = "example@company.com";
	private static String businessPhone = "Phone: (123) 456-7890";

	public static File generateInvoicePdf(Context context, Invoice invoice) {
		// Use app-specific directory for storing PDFs
		File pdfDir = new File(context.getExternalFilesDir(null), "Finverge");
		if (!pdfDir.exists()) {
			pdfDir.mkdirs(); // Create the directory if it doesn't exist
		}

		// Create a unique file name for the invoice
		File pdfFile = new File(pdfDir, "Invoice_" + invoice.getInvoiceNumber() + ".pdf");

		// Create PDF document
		PdfDocument pdfDocument = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(600, 900, 1).create();
		PdfDocument.Page page = pdfDocument.startPage(pageInfo);

		// Canvas and Paint objects for drawing the PDF content
		Canvas canvas = page.getCanvas();
		Paint paint = new Paint();
		Paint titlePaint = new Paint();
		titlePaint.setTextSize(16);
		titlePaint.setFakeBoldText(true);

		// Set margins and initial position for drawing text
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
		canvas.drawText(businessName, marginLeft, yPosition, titlePaint);
		yPosition += 20;
		canvas.drawText(businessAddress, marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText(businessCityStateZip, marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText(businessEmail, marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText(businessPhone, marginLeft, yPosition, paint);
		yPosition += 25;

		// Draw invoice details section
		paint.setTextSize(12);
		canvas.drawText("Invoice Number: " + invoice.getInvoiceNumber(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Date: " + invoice.getDate(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Customer: " + invoice.getCustomerName(), marginLeft, yPosition, paint);
		yPosition += 20;
		canvas.drawText("Total Amount: " + invoice.getTotalAmount(), marginLeft, yPosition, paint);
		yPosition += 25;

		// Itemized list of items (if any)
		canvas.drawText("Itemized List:", marginLeft, yPosition, titlePaint);
		yPosition += 20;

		// Draw the items
//		for (InvoiceItem item : invoice.getItems()) {
//			canvas.drawText(item.getItemName(), marginLeft, yPosition, paint);
//			canvas.drawText(String.valueOf(item.getQuantity()), 200, yPosition, paint);
//			canvas.drawText(String.format("%.2f", item.getUnitPrice()), 300, yPosition, paint);
//			canvas.drawText(String.format("%.2f", item.getTotalPrice()), 400, yPosition, paint);
//			yPosition += 20;
//		}

		// Save the PDF
		pdfDocument.finishPage(page);
		try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
			pdfDocument.writeTo(outputStream);
			Log.d("PdfUtils", "PDF created at: " + pdfFile.getAbsolutePath());
			Toast.makeText(context, "PDF saved to: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Log.e("PdfUtils", "Error saving PDF: " + e.getMessage());
			Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		} finally {
			pdfDocument.close();
		}

		return pdfFile; // Return the file for further handling (e.g., opening)
	}
}
