package com.bigdatanyze.ems1.util;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bigdatanyze.ems1.model.InvoiceItem;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

	@TypeConverter
	public static List<InvoiceItem> fromString(String value) {
		Type listType = new TypeToken<List<InvoiceItem>>() {}.getType();
		return new Gson().fromJson(value, listType);
	}

	@TypeConverter
	public static String fromList(List<InvoiceItem> list) {
		Gson gson = new Gson();
		return gson.toJson(list);
	}
}
