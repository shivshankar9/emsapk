package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {

	@PrimaryKey(autoGenerate = true)
	private int id;

	private String itemName;
	private double itemPrice;
	private int quantity;  // Added quantity field
	private double totalPrice;  // Added total price field

	// Default constructor - Room requires this for entity creation
	public Item() {
	}

	// Constructor with itemName, quantity, itemPrice, and totalPrice
	public Item(String itemName, int quantity, double itemPrice, double totalPrice) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		this.totalPrice = totalPrice;
	}

	// Constructor with itemName and itemPrice
	@Ignore
	public Item(String itemName, double itemPrice) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.quantity = 1;  // Default quantity is 1
		this.totalPrice = itemPrice;  // Default total price is the price of a single item
	}

	// Getter and Setter for id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Getter and Setter for itemName
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	// Getter and Setter for itemPrice
	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
		calculateTotalPrice();  // Recalculate totalPrice when itemPrice is updated
	}

	// Getter and Setter for quantity
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		calculateTotalPrice();  // Recalculate totalPrice when quantity is updated
	}

	// Getter and Setter for totalPrice
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	// Method to calculate total price based on quantity and itemPrice
	public void calculateTotalPrice() {
		this.totalPrice = this.itemPrice * this.quantity;
	}
}
