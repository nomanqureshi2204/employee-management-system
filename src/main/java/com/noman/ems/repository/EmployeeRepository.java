package com.noman.ems.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	Optional<Employee> findTopByOrderByEmployeeIdDesc();
	Optional<Employee> findByUser_Email(String email);
	List<Employee>findByjoiningDateBetween(LocalDate startDate,LocalDate endDate);
	List<Employee>findByProjectIsNull();
}











