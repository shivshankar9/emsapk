//package com.bigdatanyze.ems1.controller;
//
//import com.bigdatanyze.ems1.model.Employee;
//import com.bigdatanyze.ems1.service.EmployeeService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@RestController
////@RequestMapping("/api/employees")
//public class EmployeeController {
//
//	@Autowired
//	private EmployeeService employeeService;
//
//	@GetMapping
//	public ResponseEntity<List<Employee>> getAllEmployees() {
//		List<Employee> employees = employeeService.getAllEmployees();
//		return ResponseEntity.ok(employees);
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
//		return employeeService.getEmployeeById(id)
//				.map(employee -> ResponseEntity.ok(employee))
//				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//	}
//
//	@PostMapping
//	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
//		Employee createdEmployee = employeeService.saveEmployee(employee);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
//		if (employeeService.getEmployeeById(id).isPresent()) {
//			employee.setId(id);
//			Employee updatedEmployee = employeeService.saveEmployee(employee);
//			return ResponseEntity.ok(updatedEmployee);
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
//		if (employeeService.getEmployeeById(id).isPresent()) {
//			employeeService.deleteEmployee(id);
//			return ResponseEntity.noContent().build();
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
//	}
//}
