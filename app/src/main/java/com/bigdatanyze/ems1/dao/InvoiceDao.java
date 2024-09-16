package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bigdatanyze.ems1.model.Invoice;

import java.util.List;

@Dao
public interface InvoiceDao {

	@Insert
	void insert(Invoice invoice);

	@Update
	void update(Invoice invoice);

	@Delete
	void delete(Invoice invoice);

	@Query("SELECT * FROM invoice ORDER BY date DESC")
	LiveData<List<Invoice>> getAllInvoices();

	@Query("SELECT invoiceNumber FROM invoice ORDER BY id DESC LIMIT 1")
	LiveData<String> getLastInvoiceNumber();
}
