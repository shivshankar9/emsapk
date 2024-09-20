package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.InvoiceItem;

import java.util.List;

public class InvoiceItemAdapter extends RecyclerView.Adapter<InvoiceItemAdapter.InvoiceItemViewHolder> {

	private List<InvoiceItem> invoiceItemList;

	public InvoiceItemAdapter(List<InvoiceItem> invoiceItemList) {
		this.invoiceItemList = invoiceItemList;
	}

	@NonNull
	@Override
	public InvoiceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_item, parent, false); // Ensure correct layout file is used
		return new InvoiceItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull InvoiceItemViewHolder holder, int position) {
		InvoiceItem item = invoiceItemList.get(position);

		holder.itemNameTextView.setText(item.getItemName());
		holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
		holder.unitPriceTextView.setText(String.valueOf(item.getUnitPrice()));
		holder.totalPriceTextView.setText(String.valueOf(item.getTotalPrice()));
	}

	@Override
	public int getItemCount() {
		return invoiceItemList != null ? invoiceItemList.size() : 0;
	}

	public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
		this.invoiceItemList = invoiceItemList;
		notifyDataSetChanged(); // Consider using DiffUtil for better performance
	}

	public static class InvoiceItemViewHolder extends RecyclerView.ViewHolder {
		TextView itemNameTextView, quantityTextView, unitPriceTextView, totalPriceTextView;

		public InvoiceItemViewHolder(@NonNull View itemView) {
			super(itemView);
			itemNameTextView = itemView.findViewById(R.id.tvItemName);
			quantityTextView = itemView.findViewById(R.id.tvItemQty);
			unitPriceTextView = itemView.findViewById(R.id.tvItemPrice);
			totalPriceTextView = itemView.findViewById(R.id.unit_price_edit_text);
		}
	}
}
