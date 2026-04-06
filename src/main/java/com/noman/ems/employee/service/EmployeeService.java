package com.noman.ems.employee.service;

import java.util.List;

import com.noman.ems.employee.entity.Employee;

public interface EmployeeService {
	// add Employee 
	Employee addEmployee(Employee employee);
	
	//set password by using token 
	String setPassword(String token,String password);
	
	//get all Employees 
	List<Employee>getAllEmployees();
}






