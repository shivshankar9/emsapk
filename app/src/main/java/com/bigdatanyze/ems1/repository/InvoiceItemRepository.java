//package com.bigdatanyze.ems1.repository;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//
//import com.bigdatanyze.ems1.dao.InvoiceItemDao;
//import com.bigdatanyze.ems1.database.AppDatabase;
//import com.bigdatanyze.ems1.model.InvoiceItem;
//
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//
//public class InvoiceItemRepository {
//
//	private final InvoiceItemDao invoiceItemDao;
//	private final ExecutorService executorService;
//
//	public InvoiceItemRepository(Application application) {
//		AppDatabase database = AppDatabase.getDatabase(application);
//		invoiceItemDao = database.invoiceItemDao();
//		executorService = AppDatabase.databaseWriteExecutor;
//	}
//
//	// Fetch invoice items by invoice number
//	public LiveData<List<InvoiceItem>> getInvoiceItemsByInvoiceNumber(String invoiceNumber) {
//		return invoiceItemDao.getInvoiceItemsByInvoiceNumber(invoiceNumber);
//	}
//
//	// Insert a list of invoice items
//	public void insertInvoiceItems(List<InvoiceItem> invoiceItems) {
//		executorService.execute(() -> invoiceItemDao.insertInvoiceItems(invoiceItems));
//	}
//
//	// Update a list of invoice items
//	public void updateInvoiceItems(List<InvoiceItem> invoiceItems) {
//		executorService.execute(() -> invoiceItemDao.updateInvoiceItems(invoiceItems));
//	}
//
//	// Delete invoice items for a specific invoice number
//	public void deleteInvoiceItemsByInvoiceNumber(String invoiceNumber) {
//		executorService.execute(() -> invoiceItemDao.deleteInvoiceItemsByInvoiceNumber(invoiceNumber));
//	}
//}
