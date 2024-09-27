package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "business_profile") // Specify the table name
public class BusinessProfile implements Serializable {

	@PrimaryKey(autoGenerate = true) // Assuming you want an auto-generated primary key
	private int id; // Unique ID for each BusinessProfile

	private String businessName;
	private String logoUri;

	public BusinessProfile(String businessName, String logoUri) {
		this.businessName = businessName;
		this.logoUri = logoUri;
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getLogoUri() {
		return logoUri;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}
}
