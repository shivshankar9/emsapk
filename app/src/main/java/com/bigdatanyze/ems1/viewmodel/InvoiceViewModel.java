package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.bigdatanyze.ems1.model.Invoice;
import com.bigdatanyze.ems1.model.Party;
import com.bigdatanyze.ems1.repository.InvoiceRepository;

import java.util.ArrayList;
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

	public void insert(Invoice invoice) {
		repository.insert(invoice);
	}

	public void update(Invoice invoice) {
		repository.update(invoice);
	}

	public void delete(Invoice invoice) {
		repository.delete(invoice);
	}

	public LiveData<String> getLastInvoiceNumber() {
		return repository.getLastInvoiceNumber();
	}

	// Ensure this method in your repository returns LiveData<List<Party>>
	public LiveData<List<Party>> getAllParties() {
		return repository.getAllParties();
	}



	public LiveData<List<String>> getAllPartyNames() {
		// This maps the LiveData<List<Party>> to LiveData<List<String>>
		return Transformations.map(getAllParties(), parties -> {
			List<String> names = new ArrayList<>();
			for (Party party : parties) {
				names.add(party.getName());
			}
			return names;
		});
	}
}
