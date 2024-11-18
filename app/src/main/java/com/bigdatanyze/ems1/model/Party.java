package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "party")
public class Party {
	@PrimaryKey(autoGenerate = true)
	private int id;
	private String name;
	private String contact;
	private String gstin;
	private String billing_address;
	private String shipping_address;

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}








	// Constructor
//	public Party(String name, String contact) {
//		this.name = name;
//		this.contact = contact;
//	}
	public Party(String name, String contact,String gstin,String billing_address,String shipping_address) {
		this.name = name;
		this.contact = contact;
		this.gstin=gstin;
		this.billing_address=billing_address;
		this.shipping_address=shipping_address;

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
