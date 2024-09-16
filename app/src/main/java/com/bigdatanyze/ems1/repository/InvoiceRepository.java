package com.bigdatanyze.ems1.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.bigdatanyze.ems1.dao.InvoiceDao;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.Invoice;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class InvoiceRepository {

	private InvoiceDao invoiceDao;
	private LiveData<List<Invoice>> allInvoices;
	private final ExecutorService executorService;

	public InvoiceRepository(Application application) {
		AppDatabase database = AppDatabase.getDatabase(application);
		invoiceDao = database.invoiceDao();
		allInvoices = invoiceDao.getAllInvoices();
		executorService = AppDatabase.databaseWriteExecutor;
	}

	public LiveData<List<Invoice>> getAllInvoices() {
		return allInvoices;
	}

	public void insert(Invoice invoice) {
		executorService.execute(() -> invoiceDao.insert(invoice));
	}

	public void update(Invoice invoice) {
		executorService.execute(() -> invoiceDao.update(invoice));
	}

	public void delete(Invoice invoice) {
		executorService.execute(() -> invoiceDao.delete(invoice));
	}

	public LiveData<String> getLastInvoiceNumber() {
		return invoiceDao.getLastInvoiceNumber();
	}
}
