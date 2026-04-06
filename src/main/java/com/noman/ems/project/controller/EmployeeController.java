package com.noman.ems.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	
	@PostMapping
	public ResponseEntity<Employee>addEmployee(@RequestBody Employee employee){
		Employee saved = employeeService.addEmployee(employee);
		
		return new ResponseEntity<>(saved,HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<Employee>getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable String id) {
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/email")
	public Employee getEmployeeByEmail(@RequestParam String email) {
		return employeeService.getEmployeeByEmail(email);
	}
	
	@GetMapping("/date-range")
	public List<Employee>getEmployeeByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
		return employeeService.getEmployeesByDateRange(start, end);
	}
	
	//update Employee
	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable String id,@RequestBody Employee employee) {
		return employeeService.updateEmployee(id, employee);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteEmployee(@PathVariable String id){
		employeeService.deleteEmployee(id);
		
		return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
	}
	
}













