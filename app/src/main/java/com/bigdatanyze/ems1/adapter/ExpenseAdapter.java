package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.model.Expense;
import com.bigdatanyze.ems1.R;
import java.util.ArrayList;
import java.util.List;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

	private List<Expense> expenseList = new ArrayList<>();

	@NonNull
	@Override
	public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.expense_item, parent, false);
		return new ExpenseViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
		Expense currentExpense = expenseList.get(position);
		holder.textViewName.setText(currentExpense.getName());
		holder.textViewPosition.setText(currentExpense.getPosition());
	}

	@Override
	public int getItemCount() {
		return expenseList.size();
	}

	public void setExpenseList(List<Expense> expenses) {
		this.expenseList = expenses;
		notifyDataSetChanged();
	}

	class ExpenseViewHolder extends RecyclerView.ViewHolder {
		private final TextView textViewName;
		private final TextView textViewPosition;

		private ExpenseViewHolder(View itemView) {
			super(itemView);
			textViewName = itemView.findViewById(R.id.text_view_name);
			textViewPosition = itemView.findViewById(R.id.text_view_position);
		}
	}
}
