package com.noman.ems.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.service.EmployeeService;


import org.springframework.web.bind.annotation.RequestBody; 

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired EmployeeService service;
	
	//create 
	@PostMapping
	public Employee add(@RequestBody Employee emp) {
		return service.add(emp);
	}
	
	//read all 
	@GetMapping
	public List<Employee>getAll(){
		return service.getAllEmployees();
	}
	
	//read by id 
	@GetMapping("/{id}")
	public Employee getById(@PathVariable String id) {
		return service.getEmployeeById(id);
	}
	
	//update 
	@PutMapping("/{id}")
	public Employee update(@PathVariable String id,@RequestBody Employee emp) {
		return service.updateEmployee(id, emp);
	}
	
	//delete 
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		service.deleteEmployee(id);
		return "Deleted Successfully";
	}
}












