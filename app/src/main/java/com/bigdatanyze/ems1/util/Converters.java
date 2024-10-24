package com.bigdatanyze.ems1.util;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bigdatanyze.ems1.model.InvoiceItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

	@TypeConverter
	public static List<InvoiceItem> fromString(String value) {
		if (value == null || value.isEmpty()) {
			return new ArrayList<>(); // Return an empty list if the input is null or empty
		}
		Log.d("Converters", "Parsing JSON: " + value);
		Type listType = new TypeToken<List<InvoiceItem>>() {}.getType();
		return new Gson().fromJson(value, listType);
	}

	@TypeConverter
	public static String fromList(List<InvoiceItem> list) {
		if (list == null || list.isEmpty()) {
			return "[]"; // Return an empty JSON array if the list is null or empty
		}
		Gson gson = new Gson();
		return gson.toJson(list);
	}
}
