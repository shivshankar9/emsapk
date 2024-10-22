package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.ViewHolder> {

	private final List<String> recentActivities;

	public RecentActivityAdapter(List<String> recentActivities) {
		this.recentActivities = recentActivities;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(android.R.layout.simple_list_item_1, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.textView.setText(recentActivities.get(position));
	}

	@Override
	public int getItemCount() {
		return recentActivities.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		TextView textView;

		ViewHolder(View itemView) {
			super(itemView);
			textView = itemView.findViewById(android.R.id.text1);
		}
	}
}
