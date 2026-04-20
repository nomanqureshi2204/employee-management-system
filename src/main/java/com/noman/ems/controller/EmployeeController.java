package com.noman.ems.controller;

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

import com.noman.ems.dto.EmployeeResponseDto;
import com.noman.ems.entity.Employee;
import com.noman.ems.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody; 

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired EmployeeService service;
	
	//create 
	@PostMapping
	@Operation(summary = "Add Eemployee")
	public Employee add(@RequestBody Employee emp) {
		return service.add(emp);
	}
	
	//read all 
	@GetMapping
	@Operation(summary = "Get All Employees")
	public List<EmployeeResponseDto>getAll(){
		return service.getAllEmployeesDto();
	}
	
	//read by id 
	@GetMapping("/{id}")
	@Operation(summary = "Get Employee By Id")
	public EmployeeResponseDto getById(@PathVariable String id) {
		return service.getEmployeeByIdDto(id);
	}
	
	//update 
	@PutMapping("/{id}")
	@Operation(summary = "Update Employee")
	public Employee update(@PathVariable String id,@RequestBody Employee emp) {
		
		return service.updateEmployee(id,emp);
	}
	
	//delete 
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Employee")
	public String delete(@PathVariable String id) {
		service.deleteEmployee(id);
		return "Deleted Successfully";
	}
	
	// get employee basis of email
	@GetMapping("/email/{email}")
	@Operation(summary = "Get Employee By Email")
	public EmployeeResponseDto getByEmail(@PathVariable String email) {
		return service.getEmployeeByEmailDto(email);
	}
	
	@GetMapping("date-range")
	@Operation(summary = "Get Employee By Date-Range")
	public List<EmployeeResponseDto>getByDateRange(
			@RequestParam LocalDate start,
			@RequestParam LocalDate endDate){
		
		return service.getEmployeesByDateRangeDto(start, endDate);
	}	
	
	@PutMapping("/{empId}/assign/{projectId}")
	@Operation(summary = "Assign Employee To Project")
	public Employee assignProject(
			@PathVariable String empId,
			@PathVariable String projectId) {
		
		return service.assignEmployeeToProject(empId, projectId);
	}
	
	@PutMapping("/{empId}/release/{projectId}")
	@Operation(summary = "Release Employee From Project")
	public String releaseProject(
			@PathVariable String empId,
			@PathVariable String projectId) {
		
		return service.releaseEmployeeFromProject(empId, projectId);
	}
	
	@GetMapping("/bench")
	@Operation(summary = "Get Bench Employees")
    public List<EmployeeResponseDto>getBenchEmployees(){
    	return service.getBenchEmployeeDto();
    }
	
	
}












