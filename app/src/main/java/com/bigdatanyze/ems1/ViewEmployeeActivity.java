//package com.bigdatanyze.ems1;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
//import com.bigdatanyze.ems1.model.Employee;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ViewEmployeeActivity extends AppCompatActivity implements EmployeeAdapter.OnItemActionListener {
//
//	private RecyclerView recyclerView;
//	private EmployeeAdapter employeeAdapter;
//	private List<Employee> employeeList;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_view_employee);
//
//		recyclerView = findViewById(R.id.recyclerView);
//		recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//		// Initialize employee list (replace with actual data fetching logic)
//		employeeList = new ArrayList<>();
//		// Add dummy data
//		employeeList.add(new Employee("John Doe", "Manager", "Sales"));
//		employeeList.add(new Employee("Jane Smith", "Developer", "IT"));
//
//		// Set up the adapter and pass `this` to handle edit and delete actions
//		employeeAdapter = new EmployeeAdapter(this);
//		employeeAdapter.setEmployeeList(employeeList);
//		recyclerView.setAdapter(employeeAdapter);
//	}
//
//	// This method will handle edit action for an employee
//	@Override
//	public void onEditClicked(Employee employee) {
//		// Open EditEmployeeActivity to edit employee details
//		Intent intent = new Intent(this, EditEmployeeActivity.class);
//		intent.putExtra("employee", employee); // Pass employee object to the next activity
//		startActivity(intent);
//	}
//
//	// This method will handle delete action for an employee
//	@Override
//	public void onDeleteClicked(Employee employee) {
//		// Remove employee from the list
//		employeeList.remove(employee);
//		employeeAdapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
//		Toast.makeText(this, employee.getName() + " deleted", Toast.LENGTH_SHORT).show();
//	}
//}
