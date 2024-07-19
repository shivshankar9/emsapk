package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.bigdatanyze.ems1.model.Employee;
import java.util.List;

@Dao
public interface EmployeeDao {
	@Insert
	void insert(Employee employee);

	@Query("SELECT * FROM employee_table")
	LiveData<List<Employee>> getAllEmployees();
}
