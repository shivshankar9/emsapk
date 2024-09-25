//package com.bigdatanyze.ems1.viewmodel;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//import com.bigdatanyze.ems1.model.InvoiceItem;
//import com.bigdatanyze.ems1.repository.InvoiceItemRepository;
//
//import java.util.List;
//
//public class InvoiceItemViewModel extends ViewModel {
//
//	private final InvoiceItemRepository invoiceItemRepository;
//	private LiveData<List<InvoiceItem>> itemsByInvoiceNumber;
//
//	// Constructor with dependency injection
//	public InvoiceItemViewModel(InvoiceItemRepository repository) {
//		this.invoiceItemRepository = repository;
//	}
//
//	// Method to fetch invoice items by invoice number
//	public LiveData<List<InvoiceItem>> getItemsByInvoiceNumber(String invoiceNumber) {
//		if (itemsByInvoiceNumber == null) {
//			itemsByInvoiceNumber = invoiceItemRepository.getInvoiceItemsByInvoiceNumber(invoiceNumber);
//		}
//		return itemsByInvoiceNumber;
//	}
//}
