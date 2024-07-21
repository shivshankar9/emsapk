package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.repository.EmployeeRepository;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
	private EmployeeRepository repository;
	private LiveData<List<Employee>> allEmployees;

	public EmployeeViewModel(@NonNull Application application) {
		super(application);
		repository = new EmployeeRepository(application);
		allEmployees = repository.getAllEmployees();
	}

	public LiveData<List<Employee>> getAllEmployees() {
		return allEmployees;
	}

	public LiveData<Employee> getEmployeeById(Long id) {
		return repository.getEmployeeById(id);
	}

	public void insert(Employee employee) {
		repository.insert(employee);
	}

	public void update(Employee employee) {
		repository.update(employee);
	}

	public void delete(Employee employee) {
		repository.delete(employee);
	}

	public static class Factory extends ViewModelProvider.NewInstanceFactory {
		private final Application application;

		public Factory(Application application) {
			this.application = application;
		}

		@NonNull
		@Override
		@SuppressWarnings("unchecked")
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			if (modelClass.isAssignableFrom(EmployeeViewModel.class)) {
				return (T) new EmployeeViewModel(application);
			}
			throw new IllegalArgumentException("Unknown ViewModel class");
		}
	}
}
