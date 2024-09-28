package com.bigdatanyze.ems1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder> {

	private List<String> suggestions;

	public SuggestionsAdapter(List<String> suggestions) {
		this.suggestions = suggestions;
	}

	@NonNull
	@Override
	public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
		return new SuggestionViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
		String suggestion = suggestions.get(position);
		holder.suggestionTextView.setText(suggestion);
		holder.itemView.setOnClickListener(v -> {
			// Handle click on suggestion
			// For example, set the suggestion in the input field
			// (You could also navigate to a new fragment here)
		});
	}

	@Override
	public int getItemCount() {
		return suggestions.size();
	}

	public void updateSuggestions(List<String> newSuggestions) {
		this.suggestions.clear();
		this.suggestions.addAll(newSuggestions);
		notifyDataSetChanged();
	}

	static class SuggestionViewHolder extends RecyclerView.ViewHolder {
		TextView suggestionTextView;

		SuggestionViewHolder(View itemView) {
			super(itemView);
			suggestionTextView = itemView.findViewById(android.R.id.text1);
		}
	}
}
