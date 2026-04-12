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
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final IdGenerator idGenerator;
	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private ProjectRepository projectRepo;

	EmployeeServiceImpl(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Override
	public Employee add(Employee emp) {
		// get last Employee ID from DB
		String lastId = employeeRepo.findTopByOrderByEmployeeIdDesc().map(Employee::getEmployeeId).orElse(null);

		// generate new ID safely
		emp.setEmployeeId(idGenerator.generateEmployeeId(lastId));

		// set joining date if null

		if (emp.getJoiningDate() == null) {
			emp.setJoiningDate(LocalDate.now());
		}
		return employeeRepo.save(emp);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}

	@Override
	public Employee getEmployeeById(String id) {
		return employeeRepo.findById(id).orElse(null);
	}

	@Override
	public void deleteEmployee(String id) {
		if (!employeeRepo.existsById(id)) {
			throw new RuntimeException("Invalid Employee ID");
		}
		employeeRepo.deleteById(id);
	}

	@Override
	public Employee updateEmployee(String id, Employee emp) {
		Optional<Employee> existing = employeeRepo.findById(id);
		if (existing.isPresent()) {
			Employee old = existing.get();
			old.setName(emp.getName());
			old.setDept(emp.getDept());
			old.setPhone(emp.getPhone());
			old.setProject(emp.getProject()); // project can be null
			
			String empid = old.getEmployeeId();
			
			
		
			return employeeRepo.save(old);
		}
		return null; // throw exception
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Empoyee not found with email: " + email));
	}
	
	@Override
	public List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end) {
		return employeeRepo.findByjoiningDateBetween(start, end);
	}

	// on-board an employee on a project.
	@Override
	public Employee assignEmployeeToProject(String empId, String projectId) {
		Employee emp = employeeRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found"));

		Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

		emp.setProject(project);

		return employeeRepo.save(emp);
	}
	
	@Override
	public String releaseEmployeeFromProject(String empId, String projectId) {
		Employee emp = employeeRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found"));

		Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

		emp.setProject(null);

		employeeRepo.save(emp);
		
		return "project has been released from "+emp.getName();
	}
	
	@Override
	public List<Employee> getBenchEmployee(){
		return employeeRepo.findByProjectIsNull();
	}

}











