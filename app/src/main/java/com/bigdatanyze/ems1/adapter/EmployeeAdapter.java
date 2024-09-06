package com.bigdatanyze.ems1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.R;
import com.bigdatanyze.ems1.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

	private List<Employee> employeeList = new ArrayList<>();
	private final OnItemActionListener onItemActionListener;

	// Constructor to pass the action listener interface
	public EmployeeAdapter(OnItemActionListener onItemActionListener) {
		this.onItemActionListener = onItemActionListener;
	}

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

		// Set onClick listeners for edit and delete buttons
		holder.buttonEdit.setOnClickListener(v -> onItemActionListener.onEditClicked(currentEmployee));
		holder.buttonDelete.setOnClickListener(v -> onItemActionListener.onDeleteClicked(currentEmployee));
	}

	@Override
	public int getItemCount() {
		return employeeList.size();
	}

	public void setEmployeeList(List<Employee> employees) {
		this.employeeList = employees;
		notifyDataSetChanged();
	}

	class EmployeeViewHolder extends RecyclerView.ViewHolder {
		private final TextView textViewName;
		private final TextView textViewPosition;
		private final Button buttonEdit;
		private final Button buttonDelete;

		private EmployeeViewHolder(View itemView) {
			super(itemView);
			textViewName = itemView.findViewById(R.id.text_view_name);
			textViewPosition = itemView.findViewById(R.id.text_view_position);
			buttonEdit = itemView.findViewById(R.id.button_edit);
			buttonDelete = itemView.findViewById(R.id.button_delete);
		}
	}

	// Define the interface to handle item click events
	public interface OnItemActionListener {
		void onEditClicked(Employee employee);
		void onDeleteClicked(Employee employee);
	}
}
