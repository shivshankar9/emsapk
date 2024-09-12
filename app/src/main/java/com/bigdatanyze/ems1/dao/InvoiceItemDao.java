package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bigdatanyze.ems1.model.InvoiceItem;

import java.util.List;

@Dao
public interface InvoiceItemDao {

	@Insert
	void insert(InvoiceItem invoiceItem);

	@Update
	void update(InvoiceItem invoiceItem);

	@Delete
	void delete(InvoiceItem invoiceItem);

	@Query("SELECT * FROM invoice_item")
	LiveData<List<InvoiceItem>> getAllInvoiceItems();
}
