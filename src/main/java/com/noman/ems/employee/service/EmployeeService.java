package com.noman.ems.employee.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepsitory;

	// Add Employee
	public Employee addEmployee(Employee employee) {
		return employeeRepsitory.save(employee);
	}

	// Get All Employees
	public List<Employee> getAllEmployees() {
		return employeeRepsitory.findAll();
	}

	// get Employee By Id
	public Employee getEmployeeById(String id) {
		return employeeRepsitory.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
	}

	// get Employee by Email
	public Employee getEmployeeByEmail(String email) {
		return employeeRepsitory.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee not found"));
	}

	// get Employee by Date range
	public List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end) {
		return employeeRepsitory.findByDateOfJoiningBetween(start, end);
	}

	// ✅ DELETE EMPLOYEE
	public void deleteEmployee(String id) {
		employeeRepsitory.deleteById(id);
	}

	// update Employee(id & email change nahin hoga)
	public Employee updateEmployee(String id, Employee employee) {
		Employee existing = getEmployeeById(id);

		existing.setName(employee.getName());
		existing.setPhone(employee.getPhone());
		existing.setDepartment(employee.getDepartment());
		existing.setDateOfJoining(employee.getDateOfJoining());
		existing.setPassword(employee.getPassword());
		existing.setFailedAttempts(employee.getFailedAttempts());
		existing.setAccountLockedUntil(employee.getAccountLockedUntil());
		existing.setProject(employee.getProject());

		return employeeRepsitory.save(existing);

	}

}
