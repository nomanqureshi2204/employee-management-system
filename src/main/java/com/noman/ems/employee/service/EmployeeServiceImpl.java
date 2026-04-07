package com.noman.ems.employee.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noman.ems.common.entity.Token;
import com.noman.ems.common.repository.TokenRepository;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public Employee save(Employee emp) {
		return repo.save(emp);
	}
	
	@Override
	public List<Employee> getAll() {
		return repo.findAll();
	}
	
	@Override
	public Employee getById(String id){
		return repo.findById(id).orElse(null);
	}
	
	@Override
	public void delete(String id) {
		repo.deleteById(id);
	}
	
}














