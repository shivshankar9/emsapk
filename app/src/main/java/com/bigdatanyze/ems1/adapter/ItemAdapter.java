package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {

	private List<Item> items = new ArrayList<>();
	private List<Item> itemsFull; // Full list for filtering

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
		itemsFull = new ArrayList<>(items); // Store the full list for filtering
		notifyDataSetChanged();
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				List<Item> filteredList = new ArrayList<>();
				if (constraint == null || constraint.length() == 0) {
					filteredList.addAll(itemsFull);
				} else {
					String filterPattern = constraint.toString().toLowerCase().trim();
					for (Item item : itemsFull) {
						if (item.getItemName().toLowerCase().contains(filterPattern)) {
							filteredList.add(item);
						}
					}
				}
				FilterResults results = new FilterResults();
				results.values = filteredList;
				return results;
			}

			@Override
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint, FilterResults results) {
				items.clear();
				items.addAll((List) results.values);
				notifyDataSetChanged();
			}
		};
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
