package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "invoice_item")
public class InvoiceItem {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String itemName;
	private int quantity;
	private double unitPrice;
	private double totalPrice;

	// Default constructor
	public InvoiceItem() {
	}

	// Constructor with parameters
	@Ignore
	public InvoiceItem(String itemName, int quantity, double unitPrice, double totalPrice) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}

	// Constructor with parameters (without total price calculation)
	@Ignore
	public InvoiceItem(String itemName, int quantity, double unitPrice) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = quantity * unitPrice;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		setTotalPrice(this.unitPrice * quantity); // Update total price when quantity changes
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
		setTotalPrice(this.unitPrice * this.quantity); // Update total price when unit price changes
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	// Make the setter public to allow Room to access it
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setPrice(double v) {
	}

	public void setDiscount(double discount) {
	}
}
