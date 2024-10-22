package com.bigdatanyze.ems1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Invoice;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

	private List<Invoice> invoiceList;
	private final Context context;
	private final OnInvoiceClickListener onInvoiceClickListener;

	public InvoiceAdapter(Context context, List<Invoice> invoiceList, OnInvoiceClickListener onInvoiceClickListener) {
		this.context = context;
		this.invoiceList = invoiceList;
		this.onInvoiceClickListener = onInvoiceClickListener;
	}

	@NonNull
	@Override
	public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item, parent, false);
		return new InvoiceViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
		Invoice invoice = invoiceList.get(position);
		holder.invoiceNumberTextView.setText("Invoice Number: " + invoice.getInvoiceNumber());
		holder.dateTextView.setText("Date: " + invoice.getDate());
		holder.amountTextView.setText("Amount: $" + invoice.getTotalAmount());

		holder.pdfIcon.setOnClickListener(v -> onInvoiceClickListener.onInvoiceClick(invoice));
	}

	@Override
	public int getItemCount() {
		return invoiceList.size();
	}

	public void updateInvoices(List<Invoice> invoices) {
		this.invoiceList = invoices;
		notifyDataSetChanged();
	}

	public interface OnInvoiceClickListener {
		void onInvoiceClick(Invoice invoice);
	}

	class InvoiceViewHolder extends RecyclerView.ViewHolder {
		TextView invoiceNumberTextView;
		TextView dateTextView;
		TextView amountTextView;
		ImageView pdfIcon;
		CardView cardView;

		public InvoiceViewHolder(@NonNull View itemView) {
			super(itemView);
			invoiceNumberTextView = itemView.findViewById(R.id.invoice_number);
			dateTextView = itemView.findViewById(R.id.date);
			amountTextView = itemView.findViewById(R.id.amount);
			pdfIcon = itemView.findViewById(R.id.pdf_icon);
			cardView = itemView.findViewById(R.id.card_view);
		}
	}
}
