package com.bigdatanyze.ems1.api;

import com.bigdatanyze.ems1.model.Employee;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface EmployeeApi {
	@GET("employees")
	Call<List<Employee>> getAllEmployees();

	@GET("employees/{id}")
	Call<Employee> getEmployeeById(@Path("id") Long id);

	@POST("employees")
	Call<Employee> addEmployee(@Body Employee employee);

	@PUT("employees/{id}")
	Call<Employee> updateEmployee(@Path("id") Long id, @Body Employee employee);

	@DELETE("employees/{id}")
	Call<Void> deleteEmployee(@Path("id") Long id);
}
