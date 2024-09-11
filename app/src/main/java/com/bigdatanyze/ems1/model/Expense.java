package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String amount;
	private String category;
	private String date;
	private String description;

	// Default constructor
	public Expense() {
	}

	// Constructor with parameters
	@Ignore
	public Expense(String amount, String category, String date, String description) {
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.description = description;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
