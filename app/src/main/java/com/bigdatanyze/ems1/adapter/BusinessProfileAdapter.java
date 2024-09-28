package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.BusinessProfile;
import java.util.List;

public class BusinessProfileAdapter extends RecyclerView.Adapter<BusinessProfileAdapter.ViewHolder> {

	private List<BusinessProfile> businessProfiles;
	private OnProfileClickListener listener;

	public BusinessProfileAdapter(List<BusinessProfile> businessProfiles, OnProfileClickListener listener) {
		this.businessProfiles = businessProfiles;
		this.listener = listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_business_profile, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		BusinessProfile profile = businessProfiles.get(position);
		holder.tvBusinessName.setText(profile.getBusinessName());

		holder.itemView.setOnClickListener(v -> listener.onProfileClick(profile)); // Handle click events
	}

	@Override
	public int getItemCount() {
		return businessProfiles.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView tvBusinessName;

		public ViewHolder(View itemView) {
			super(itemView);
			tvBusinessName = itemView.findViewById(R.id.tvBusinessName);
		}
	}

	public interface OnProfileClickListener {
		void onProfileClick(BusinessProfile profile);
	}
}
