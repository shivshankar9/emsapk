package com.bigdatanyze.ems1.dao;

import com.bigdatanyze.ems1.model.Employee;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface EmployeeDao {

	@Query("SELECT * FROM employees")
	LiveData<List<Employee>> getAllEmployees(); // Return LiveData

	@Query("SELECT * FROM employees WHERE id = :id")
	LiveData<Employee> getEmployeeById(Long id); // Return LiveData

	@Insert
	void insertEmployee(Employee employee);

	@Update
	void updateEmployee(Employee employee);

	@Delete
	void deleteEmployee(Employee employee);
}
