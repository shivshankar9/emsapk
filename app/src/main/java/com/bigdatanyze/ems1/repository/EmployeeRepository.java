package com.bigdatanyze.ems1.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.bigdatanyze.ems1.dao.EmployeeDao;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.Employee;

import java.util.List;

public class EmployeeRepository {
	private EmployeeDao employeeDao;
	private LiveData<List<Employee>> allEmployees;

	public EmployeeRepository(Application application) {
		AppDatabase db = AppDatabase.getDatabase(application);
		employeeDao = db.employeeDao();
		allEmployees = employeeDao.getAllEmployees(); // Use LiveData
	}

	public LiveData<List<Employee>> getAllEmployees() {
		return allEmployees;
	}

	public LiveData<Employee> getEmployeeById(Long id) {
		return employeeDao.getEmployeeById(id); // Return LiveData
	}

	public void insert(Employee employee) {
		AppDatabase.databaseWriteExecutor.execute(() -> employeeDao.insertEmployee(employee));
	}

	public void update(Employee employee) {
		AppDatabase.databaseWriteExecutor.execute(() -> employeeDao.updateEmployee(employee));
	}

	public void delete(Employee employee) {
		AppDatabase.databaseWriteExecutor.execute(() -> employeeDao.deleteEmployee(employee));
	}
}
