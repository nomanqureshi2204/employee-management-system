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
import com.noman.ems.employee.dto.EmployeeResponseDto;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	

	@Override
	public Employee add(Employee emp) {
		// get last Employee ID from DB
		String lastId = employeeRepo.findTopByOrderByEmployeeIdDesc().map(Employee::getEmployeeId).orElse(null);

		// generate new ID safely
		emp.setEmployeeId(IdGenerator.generateEmployeeId(lastId));

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

	    Employee old = employeeRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));
	    
	    

	    old.setName(emp.getName());
	    old.setDept(emp.getDept());
	    old.setPhone(emp.getPhone());
	    
	    
	    if (emp.getProject() != null && emp.getProject().getProjectId() != null) {
	    	
	    	

	        String projectId = emp.getProject().getProjectId();

	        Project project = projectRepo.findById(projectId)
	                .orElseThrow(() -> new RuntimeException("Project not found"));

	        old.setProject(project);
	    } else {
	        old.setProject(null); // optional (bench case)
	    }

	    return employeeRepo.save(old);
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
	
	
	
	
	@Override
	public String login(String email,String password) {
		Employee emp = employeeRepo.findByEmail(email)
				.orElseThrow(()->new RuntimeException("Invalid email"));
		
		//check Lock 
		if(emp.getLockTime()!=null) {
			if(emp.getLockTime().isAfter(LocalDateTime.now())) {
				throw new RuntimeException("Account locked! Try after 5 menutes");
			}else {
				emp.setFailedAttempts(0);
				emp.setLockTime(null);
				emp.setAccountLocked(false);
			}
		}
		
		//password check 
		if(passwordEncoder.matches(password, emp.getPassword())) {
			emp.setFailedAttempts(0);
			emp.setLockTime(null);
			emp.setAccountLocked(false);
			employeeRepo.save(emp);
			
			return "Login Successfull";
		}
		else {
			int attempts = emp.getFailedAttempts()+1;
			emp.setFailedAttempts(attempts);
			
			if(attempts >=5) {
				emp.setLockTime(LocalDateTime.now().plusMinutes(5));
				emp.setAccountLocked(true);
			}
			
			employeeRepo.save(emp);
			
			throw new RuntimeException("Invalid password. Attempts: " + attempts);
			
		}
	}
	
	// mapper 
	private EmployeeResponseDto convertToDto(Employee emp) {
		
		EmployeeResponseDto dto = new EmployeeResponseDto();
		
		dto.setEmployeeId(emp.getEmployeeId());
		dto.setName(emp.getName());
		dto.setDept(emp.getDept());
		dto.setEmail(emp.getEmail());
		dto.setPhone(emp.getPhone());
		
		if(emp.getProject() != null) {
			dto.setProjectId(emp.getProject().getProjectId());
		}
		
		return dto;
	}
	
	//dto method 
	public List<EmployeeResponseDto> getAllEmployeesDto(){
		
		return employeeRepo.findAll()
				.stream()
				.map(this::convertToDto)
				.toList();
	}
	
	@Override
	public EmployeeResponseDto getEmployeeByIdDto(String id) {
		Employee emp = employeeRepo.findById(id)
				.orElseThrow(()->new RuntimeException("Employee not found"));
		
		return convertToDto(emp);
	}
	
	@Override
	public EmployeeResponseDto getEmployeeByEmailDto(String email) {
		Employee emp = employeeRepo.findByEmail(email)
				.orElseThrow(()->new RuntimeException("Employee not found"));
		return convertToDto(emp);
	}
	
	@Override
	public List<EmployeeResponseDto>getBenchEmployeeDto(){
		return employeeRepo.findByProjectIsNull()
				.stream()
				.map(this::convertToDto)
				.toList();
	}
	
	@Override
	public List<EmployeeResponseDto>getEmployeesByDateRangeDto(LocalDate start,LocalDate end){
		
		return employeeRepo.findByjoiningDateBetween(start, end)
				.stream()
				.map(this::convertToDto)
				.toList();
				
	}
}











