package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Party;
import java.util.ArrayList;
import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {

	private List<Party> partyList = new ArrayList<>();

	@NonNull
	@Override
	public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_party, parent, false);
		return new PartyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {
		Party currentParty = partyList.get(position);
		holder.textViewName.setText(currentParty.getName());
		holder.textViewContact.setText(currentParty.getContact());
	}

	@Override
	public int getItemCount() {
		return partyList.size();
	}

	public void setPartyList(List<Party> parties) {
		this.partyList = parties;
		notifyDataSetChanged();
	}

	class PartyViewHolder extends RecyclerView.ViewHolder {
		private TextView textViewName;
		private TextView textViewContact;

		public PartyViewHolder(@NonNull View itemView) {
			super(itemView);
			textViewName = itemView.findViewById(R.id.textViewPartyName);
			textViewContact = itemView.findViewById(R.id.textViewPartyContact);
		}
	}
}
