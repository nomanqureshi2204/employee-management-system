package com.noman.ems.employee.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noman.ems.employee.dto.EmployeeResponseDto;
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
	public List<EmployeeResponseDto>getAll(){
		return service.getAllEmployeesDto();
	}
	
	//read by id 
	@GetMapping("/{id}")
	public EmployeeResponseDto getById(@PathVariable String id) {
		return service.getEmployeeByIdDto(id);
	}
	
	//update 
	@PutMapping("/{id}")
	public Employee update(@PathVariable String id,@RequestBody Employee emp) {
		
		return service.updateEmployee(id,emp);
	}
	
	//delete 
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		service.deleteEmployee(id);
		return "Deleted Successfully";
	}
	
	// get employee basis of email
	@GetMapping("/email/{email}")
	public EmployeeResponseDto getByEmail(@PathVariable String email) {
		return service.getEmployeeByEmailDto(email);
	}
	
	@GetMapping("date-range")
	public List<EmployeeResponseDto>getByDateRange(
			@RequestParam LocalDate start,
			@RequestParam LocalDate endDate){
		
		return service.getEmployeesByDateRangeDto(start, endDate);
	}	
	
	@PutMapping("/{empId}/assign/{projectId}")
	public Employee assignProject(
			@PathVariable String empId,
			@PathVariable String projectId) {
		
		return service.assignEmployeeToProject(empId, projectId);
	}
	
	@PutMapping("/{empId}/release/{projectId}")
	public String releaseProject(
			@PathVariable String empId,
			@PathVariable String projectId) {
		
		return service.releaseEmployeeFromProject(empId, projectId);
	}
	
	@GetMapping("/bench")
    public List<EmployeeResponseDto>getBenchEmployees(){
    	return service.getBenchEmployeeDto();
    }
	
	
}












