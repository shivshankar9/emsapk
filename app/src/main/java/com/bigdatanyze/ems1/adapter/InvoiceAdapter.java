package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Invoice;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

	private final List<Invoice> invoices;
	private final OnInvoiceClickListener listener;

	public interface OnInvoiceClickListener {
		void onInvoiceClick(Invoice invoice);
	}

	public InvoiceAdapter(List<Invoice> invoices, OnInvoiceClickListener listener) {
		this.invoices = invoices;
		this.listener = listener;
	}

	@NonNull
	@Override
	public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false); // Ensure correct layout file is used
		return new InvoiceViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
		Invoice invoice = invoices.get(position);
		holder.invoiceNumberTextView.setText(invoice.getInvoiceNumber());
		holder.customerNameTextView.setText(invoice.getCustomerName());
		holder.dateTextView.setText(invoice.getDate());

		// Set the click listener
		holder.itemView.setOnClickListener(v -> listener.onInvoiceClick(invoice));
	}

	@Override
	public int getItemCount() {
		return invoices.size();
	}

	public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
		TextView invoiceNumberTextView, customerNameTextView, dateTextView;

		public InvoiceViewHolder(@NonNull View itemView) {
			super(itemView);
			invoiceNumberTextView = itemView.findViewById(R.id.invoice_number_text_view);
			customerNameTextView = itemView.findViewById(R.id.customer_name_text_view);
			dateTextView = itemView.findViewById(R.id.date_text_view);
		}
	}
}
