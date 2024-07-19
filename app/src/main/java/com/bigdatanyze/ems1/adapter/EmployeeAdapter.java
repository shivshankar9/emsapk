package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
	private List<Employee> employeeList = new ArrayList<>();

	@NonNull
	@Override
	public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_employee, parent, false);
		return new EmployeeViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
		Employee currentEmployee = employeeList.get(position);
		holder.textViewName.setText(currentEmployee.getName());
		holder.textViewPosition.setText(currentEmployee.getPosition());
	}

	@Override
	public int getItemCount() {
		return employeeList.size();
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
		notifyDataSetChanged();
	}

	class EmployeeViewHolder extends RecyclerView.ViewHolder {
		private TextView textViewName;
		private TextView textViewPosition;

		public EmployeeViewHolder(@NonNull View itemView) {
			super(itemView);
			textViewName = itemView.findViewById(R.id.text_view_name);
			textViewPosition = itemView.findViewById(R.id.text_view_position);
		}
	}
}
