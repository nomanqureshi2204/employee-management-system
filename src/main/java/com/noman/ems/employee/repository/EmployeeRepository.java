package com.noman.ems.employee.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	
}











