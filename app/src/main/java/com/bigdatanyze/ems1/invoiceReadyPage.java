package com.bigdatanyze.ems1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bigdatanyze.ems1.model.Item;

import java.util.ArrayList;

public class invoiceReadyPage extends AppCompatActivity {

	private EditText etItemName, etItemQty, etItemPrice;
	private Button btnSaveItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_ready_page);


	}
}
