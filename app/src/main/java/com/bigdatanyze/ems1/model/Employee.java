package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee_table")
public class Employee {
	@PrimaryKey(autoGenerate = true)
	private int id;
	private String name;
	private String position;

	public Employee(String name, String position) {
		this.name = name;
		this.position = position;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
