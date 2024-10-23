package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "party")
public class Party {
	@PrimaryKey(autoGenerate = true)
	private int id;
	private String name;
	private String contact;

	public String getGstin() {
		return Gstin;
	}

	public void setGstin(String gstin) {
		Gstin = gstin;
	}

	private String Gstin;


	// Constructor
	public Party(String name, String contact) {
		this.name = name;
		this.contact = contact;
	}
	public Party(String name, String contact, String gstin) {
		this.name = name;
		this.contact = contact;
	}


	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
