package com.bigdatanyze.ems1.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "expense")
public class Expense {

		@PrimaryKey(autoGenerate = true)
		private int id;
		private String name;
		private String position;



		// Default constructor
		public Expense() {
		}

		// Constructor with parameters
		@Ignore
		public Expense(String name, String position) {
			this.name = name;
			this.position = position;
		}

		// Getters and setters
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
	

