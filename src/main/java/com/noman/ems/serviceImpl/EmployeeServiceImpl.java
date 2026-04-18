package com.noman.ems.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.dto.EmployeeResponseDto;
import com.noman.ems.entity.Employee;
import com.noman.ems.repository.EmployeeRepository;
import com.noman.ems.entity.Project;
import com.noman.ems.repository.ProjectRepository;
import com.noman.ems.service.EmployeeService;
import com.noman.ems.entity.User;
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
		user.setEnabled(true);
		user.setAccountLocked(false);
		user.setFailedAttempts(0);

		return employeeRepo.save(emp);
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