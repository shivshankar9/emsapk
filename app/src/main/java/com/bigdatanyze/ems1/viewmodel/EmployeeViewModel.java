package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.MutableLiveData;
import com.bigdatanyze.ems1.database.EmployeeDatabase;
import com.bigdatanyze.ems1.dao.EmployeeDao;
import com.bigdatanyze.ems1.model.Employee;
import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
	private final EmployeeDao employeeDao;
	private final LiveData<List<Employee>> allEmployees;

	public EmployeeViewModel(Application application) {
		super(application);
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
