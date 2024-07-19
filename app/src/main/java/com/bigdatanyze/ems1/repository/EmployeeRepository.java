package com.bigdatanyze.ems1.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.bigdatanyze.ems1.database.EmployeeDatabase;
import com.bigdatanyze.ems1.dao.EmployeeDao;
import com.bigdatanyze.ems1.model.Employee;
import java.util.List;

public class EmployeeRepository {
	private final EmployeeDao employeeDao;
	private final LiveData<List<Employee>> allEmployees;

	public EmployeeRepository(Application application) {
		EmployeeDatabase database = EmployeeDatabase.getInstance(application);
		employeeDao = database.employeeDao();
		allEmployees = employeeDao.getAllEmployees();
	}

	public LiveData<List<Employee>> getAllEmployees() {
		return allEmployees;
	}

	public void insert(Employee employee) {
		EmployeeDatabase.databaseWriteExecutor.execute(() -> employeeDao.insert(employee));
	}
}
