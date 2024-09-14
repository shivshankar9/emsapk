package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

	private List<Employee> employeeList = new ArrayList<>();
	private List<Employee> fullEmployeeList = new ArrayList<>(); // Backup list for filtering

	@NonNull
	@Override
	public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.employee_item, parent, false);
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

	public void setEmployeeList(List<Employee> employees) {
		this.employeeList = employees;
		this.fullEmployeeList = new ArrayList<>(employees); // Create backup list
		notifyDataSetChanged();
	}

	// Filter logic for search functionality
	public void filter(String query) {
		if (query == null || query.isEmpty()) {
			employeeList = new ArrayList<>(fullEmployeeList); // Reset to full list if query is empty
		} else {
			List<Employee> filteredList = new ArrayList<>();
			for (Employee employee : fullEmployeeList) {
				if (employee.getName().toLowerCase().contains(query.toLowerCase()) ||
						employee.getPosition().toLowerCase().contains(query.toLowerCase())) {
					filteredList.add(employee);
				}
			}
			employeeList = filteredList;
		}
		notifyDataSetChanged(); // Notify adapter to refresh data
	}

	class EmployeeViewHolder extends RecyclerView.ViewHolder {
		private final TextView textViewName;
		private final TextView textViewPosition;

		private EmployeeViewHolder(View itemView) {
			super(itemView);
			textViewName = itemView.findViewById(R.id.text_view_name);
			textViewPosition = itemView.findViewById(R.id.text_view_position);
		}
	}
}
