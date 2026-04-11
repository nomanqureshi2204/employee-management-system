package com.noman.ems.employee.service;

import com.noman.ems.util.IdGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
	private final IdGenerator idGenerator;
	@Autowired
	private EmployeeRepository repo;

	EmployeeServiceImpl(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	
	@Override
	public Employee add(Employee emp) {
		// get last Employee ID from DB 
		String lastId = repo.findTopByOrderByEmployeeIdDesc()
				.map(Employee::getEmployeeId)
				.orElse(null);
		
		// generate new ID safely 
		emp.setEmployeeId(idGenerator.generateEmployeeId(lastId));
		
		// set joining date if null 
		
		if(emp.getJoiningDate() == null) {
			emp.setJoiningDate(LocalDate.now());
		}
		return repo.save(emp);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}
	
	@Override
	public Employee getEmployeeById(String id){
		return repo.findById(id).orElse(null);
	}
	
	@Override
	public void deleteEmployee(String id) {
		if (!repo.existsById(id)) {
	        throw new RuntimeException("Invalid Employee ID");
	    }
		repo.deleteById(id);
	}
	
	@Override
	public Employee updateEmployee(String id,Employee emp) {
		Optional<Employee>existing = repo.findById(id);
		if(existing.isPresent()) {
			Employee old = existing.get();
			old.setName(emp.getName());
			old.setDept(emp.getDept());
			old.setPhone(emp.getPhone());
			old.setProject(emp.getProject()); // project can be null 
			return repo.save(old);
		}
		return null;  // throw exception 
	}
	
}














