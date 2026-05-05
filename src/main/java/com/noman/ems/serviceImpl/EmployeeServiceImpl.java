package com.noman.ems.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.el.lang.VariableMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.noman.ems.dto.EmployeeResponseDto;
import com.noman.ems.entity.Employee;
import com.noman.ems.repository.EmployeeRepository;
import com.noman.ems.entity.Project;
import com.noman.ems.repository.ProjectRepository;
import com.noman.ems.repository.TokenRepository;
import com.noman.ems.service.EmployeeService;
import com.noman.ems.entity.User;
import com.noman.ems.entity.VerificationToken;
import com.noman.ems.repository.UserRepository;
import com.noman.ems.util.IdGenerator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired 
	private TokenRepository tokenRepo;

	// ================= CREATE =================
	@Override
	public Employee add(Employee emp) {
		String lastId = employeeRepo.findTopByOrderByEmployeeIdDesc()
				.map(Employee::getEmployeeId)
				.orElse(null);

		emp.setEmployeeId(IdGenerator.generateEmployeeId(lastId));

		if (emp.getJoiningDate() == null) {
			emp.setJoiningDate(LocalDate.now());
		}

		User user = emp.getUser();

		if (user == null) {
			throw new RuntimeException("User is required");
		}

		user.setRole("ROLE_EMPLOYEE");
		user.setEnabled(false);
		user.setAccountLocked(false);
		user.setFailedAttempts(0);
		
		//--------save employee----------
		Employee savedEmp = employeeRepo.save(emp);
		
		User savedUser = savedEmp.getUser();
		
		//------ token generate -------------
		String token = UUID.randomUUID().toString();
		
		VerificationToken vt = new VerificationToken();
		vt.setToken(token);
		vt.setUser(savedUser);
		vt.setExpiryTime(LocalDateTime.now().plusMinutes(0));
		
		tokenRepo.save(vt);
		
		// --------------email send ---------------
		String link = "http://localhost:8080/auth/set-password?token=" + token;
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setFrom("nomanqureshi2204email@gmail.com");
		message.setSubject("set Your password");
		message.setText("click this link(valid for 5 minutes): "+link);
		
		mailSender.send(message);
		
		//=====Debug =====
		System.out.println("EMAIL SENT TO: " + user.getEmail());
	    System.out.println("TOKEN: " + token);
	    
	    return savedEmp;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	// ================= READ =================
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}

	@Override
	public Employee getEmployeeById(String id) {
		return employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepo.findByUser_Email(email).orElseThrow(() -> new RuntimeException("Employee not found"));
	}

	// ================= UPDATE =================
	@Override
	public Employee updateEmployee(String id, Employee emp) {

		Employee old = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		old.setName(emp.getName());
		old.setDept(emp.getDept());
		old.setPhone(emp.getPhone());

		return employeeRepo.save(old);
	}

	// ================= DELETE =================
	@Override
	public void deleteEmployee(String id) {
		if (!employeeRepo.existsById(id)) {
			throw new RuntimeException("Invalid Employee ID");
		}
		employeeRepo.deleteById(id);
	}

	// ================= FILTER =================
	@Override
	public List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end) {
		return employeeRepo.findByjoiningDateBetween(start, end);
	}

	// ================= PROJECT =================
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

		emp.setProject(null);

		employeeRepo.save(emp);

		return "Employee released from project";
	}

	// ================= BENCH =================
	@Override
	public List<Employee> getBenchEmployee() {
		return employeeRepo.findByProjectIsNull();
	}

	// ================= DTO =================
	private EmployeeResponseDto convertToDto(Employee emp) {

		EmployeeResponseDto dto = new EmployeeResponseDto();

		dto.setEmployeeId(emp.getEmployeeId());
		dto.setName(emp.getName());
		dto.setDept(emp.getDept());
		dto.setEmail(emp.getUser().getEmail());
		dto.setPhone(emp.getPhone());

		if (emp.getProject() != null) {
			dto.setProjectId(emp.getProject().getProjectId());
		}

		return dto;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesDto() {
		return employeeRepo.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public EmployeeResponseDto getEmployeeByIdDto(String id) {
		return convertToDto(getEmployeeById(id));
	}

	@Override
	public EmployeeResponseDto getEmployeeByEmailDto(String email) {
		return convertToDto(getEmployeeByEmail(email));
	}

	@Override
	public List<EmployeeResponseDto> getBenchEmployeeDto() {
		return employeeRepo.findByProjectIsNull().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeResponseDto> getEmployeesByDateRangeDto(LocalDate start, LocalDate end) {
		return employeeRepo.findByjoiningDateBetween(start, end).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	
}