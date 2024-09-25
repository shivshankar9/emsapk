package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

	private List<Item> items = new ArrayList<>();

	@NonNull
	@Override
	public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_row, parent, false);
		return new ItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
		Item currentItem = items.get(position);
		holder.textViewName.setText(currentItem.getItemName());
		holder.textViewPrice.setText(String.valueOf(currentItem.getItemPrice()));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void setItems(List<Item> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {
		private TextView textViewName;
		private TextView textViewPrice;

		public ItemViewHolder(@NonNull View itemView) {
			super(itemView);
			textViewName = itemView.findViewById(R.id.textViewItemName);
			textViewPrice = itemView.findViewById(R.id.textViewItemPrice);
		}
	}
}
