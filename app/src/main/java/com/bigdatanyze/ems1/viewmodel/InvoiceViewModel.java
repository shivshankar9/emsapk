package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.Item;
import com.bigdatanyze.ems1.model.Party;
import com.bigdatanyze.ems1.repository.InvoiceRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InvoiceViewModel extends AndroidViewModel {

	private InvoiceRepository repository;
	private LiveData<List<Invoice>> allInvoices;

	public InvoiceViewModel(Application application) {
		super(application);
		repository = new InvoiceRepository(application);
		allInvoices = repository.getAllInvoices();
	}

	public LiveData<List<Invoice>> getAllInvoices() {
		return allInvoices;
	}

	public LiveData<List<Invoice>> getInvoicesByDateRange(Date fromDate, Date toDate) {
		long fromTimestamp = (fromDate != null) ? fromDate.getTime() : 0;
		long toTimestamp = (toDate != null) ? toDate.getTime() : System.currentTimeMillis();
		return repository.getInvoicesByDateRange(fromTimestamp, toTimestamp);
	}

	public LiveData<List<Invoice>> getInvoicesForCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date fromDate = calendar.getTime();
		Date toDate = new Date();
		return getInvoicesByDateRange(fromDate, toDate);
	}

	public void insert(Invoice invoice) {
		repository.insert(invoice);
	}

	public void update(Invoice invoice) {
		repository.update(invoice);
	}

	public void delete(Invoice invoice) {
		repository.delete(invoice);
	}

	public LiveData<List<Party>> getAllParties() {
		return repository.getAllParties();
	}

	public LiveData<List<String>> getAllPartyNames() {
		return Transformations.map(getAllParties(), parties -> {
			List<String> names = new ArrayList<>();
			for (Party party : parties) {
				names.add(party.getName());
			}
			return names;
		});
	}

	public LiveData<List<Item>> getAllItems() {
		return repository.getAllItems();
	}

	public LiveData<String> getLastInvoiceNumber() {
		return repository.getLastInvoiceNumber();
	}

	public LiveData<Invoice> getInvoiceById(int invoiceId) {
		return repository.getInvoiceById(invoiceId); // Call the repository's method
	}
}
