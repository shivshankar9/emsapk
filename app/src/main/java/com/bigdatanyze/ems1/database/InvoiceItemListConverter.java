package com.bigdatanyze.ems1.database;

import androidx.room.TypeConverter;
import com.bigdatanyze.ems1.model.InvoiceItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemListConverter {
	@TypeConverter
	public static String fromInvoiceItemList(List<InvoiceItem> items) {
		if (items == null) {
			return null;
		}
		Gson gson = new Gson();
		return gson.toJson(items);
	}

	@TypeConverter
	public static List<InvoiceItem> toInvoiceItemList(String data) {
		if (data == null) {
			return new ArrayList<>();
		}
		Gson gson = new Gson();
		Type listType = new TypeToken<List<InvoiceItem>>() {}.getType();
		return gson.fromJson(data, listType);
	}
}
