package com.noman.ems.employee.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	//finding Employee by Email 
	Optional<Employee>findByEmail(String email);
	
	//finding Employee on Date range
	List<Employee>findByDateOfJoiningBetween(LocalDate start,LocalDate end);
	
	//Bench Employe with null project 
	List<Employee>findByProjectIsNull();
}











