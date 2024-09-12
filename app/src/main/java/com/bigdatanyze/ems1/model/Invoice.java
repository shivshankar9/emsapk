package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

// Room type converter for handling List data type
import com.bigdatanyze.ems1.util.Converters;

@Entity(tableName = "invoice")
public class Invoice {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String invoiceNumber;
	private String customerName;
	private String customerContact;
	private String date;
	private double totalAmount;

	// A list to hold items in the invoice
	@TypeConverters(Converters.class)
	private List<InvoiceItem> items;

	private String notes;

	// Default constructor
	public Invoice() {
	}

	// Constructor with parameters
	@Ignore
	public Invoice(String invoiceNumber, String customerName, String customerContact, String date, double totalAmount, List<InvoiceItem> items, String notes) {
		this.invoiceNumber = invoiceNumber;
		this.customerName = customerName;
		this.customerContact = customerContact;
		this.date = date;
		this.totalAmount = totalAmount;
		this.items = items;
		this.notes = notes;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
