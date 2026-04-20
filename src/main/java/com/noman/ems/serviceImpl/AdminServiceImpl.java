package com.noman.ems.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.dto.EmployeeResponseDto;
import com.noman.ems.entity.Employee;
import com.noman.ems.repository.EmployeeRepository;
import com.noman.ems.service.EmployeeService;
import com.noman.ems.entity.Project;
import com.noman.ems.repository.ProjectRepository;
import com.noman.ems.service.ProjectService;
import com.noman.ems.repository.UserRepository;
import com.noman.ems.entity.Client;
import com.noman.ems.service.AdminService;
import com.noman.ems.service.ClientService;
import com.noman.ems.entity.Token;
import com.noman.ems.repository.TokenRepository;
import com.noman.ems.service.EmailService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private UserRepository userRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private EmailService emailService;

    // ================= EMPLOYEE =================
    
    // ===== Add employee 
    @Override
    public Employee addEmployee(Employee emp) {
    	
    	//step 1 : save  employee 
    	Employee saved = employeeService.add(emp);
    	
    	//step 2: generate token 
    	String tokenStr = UUID.randomUUID().toString();
    	
    
    	
    	Token token = new Token();
    	token.setToken(tokenStr);
    	token.setEmail(saved.getUser().getEmail());
    	token.setExpiryTime(LocalDateTime.now().plusMinutes(5));
    	token.setUsed(false);
    	
    	tokenRepo.save(token);
    	
    	//step 3: send email 
    	String link = "http://localhost:8080/set-password?token="+tokenStr;
    	
    	emailService.sendEmail(saved.getUser().getEmail(), link);
    	
        return saved;
        
        
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeService.getEmployeeById(id);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @Override
    public List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end) {
        return employeeService.getEmployeesByDateRange(start, end);
    }

    @Override
    public Employee updateEmployee(String id, Employee emp) {
        return employeeService.updateEmployee(id, emp);
    }

    @Override
    public void deleteEmployee(String id) {
        employeeService.deleteEmployee(id);
    }

    @Override
    public Employee assignEmployeeToProject(String empId, String projectId) {
        return employeeService.assignEmployeeToProject(empId, projectId);
    }

    @Override
    public String releaseEmployeeFromProject(String empId, String projectId) {
        return employeeService.releaseEmployeeFromProject(empId, projectId);
    }

    @Override
    public List<Employee> getBenchEmployees() {
        return employeeService.getBenchEmployee();
    }

    // ================= PROJECT =================

    @Override
    public Project addProject(Project project) {
        return projectService.add(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Override
    public Project getProjectById(String id) {
        return projectService.getProjectById(id);
    }

    @Override
    public Project updateProject(String id, Project project) {
        return projectService.updateProject(id, project);
    }

    @Override
    public void deleteProject(String id) {
        projectService.deleteProject(id);
    }

    @Override
    public List<Employee> getEmployeesByProjectId(String projectId) {
        return projectService.getEmployeesByProjectId(projectId);
    }

    @Override
    public Client getClientByProjectId(String projectId) {
        return projectService.getClientByProjectId(projectId);
    }

    // ================= CLIENT =================

    @Override
    public Client addClient(Client client) {
    	
    	//step 1: save client 
    	Client saved = clientService.add(client);
    	
    	//step2: generate  token 
    	String tokenStr = UUID.randomUUID().toString();
    	
    	Token token  = new Token();
    	token.setToken(tokenStr);
    	token.setEmail(saved.getUser().getEmail());
    	token.setExpiryTime(LocalDateTime.now().plusMinutes(5));
    	token.setUsed(false);
    	
    	tokenRepo.save(token);
    	
    	//step 3: send Email 
    	String link = "http://localhost:8080/set-password?token="+tokenStr;
    	
    	emailService.sendEmail(saved.getUser().getEmail(), link);
    	
    	return saved;
    }

    @Override
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @Override
    public Client getClientById(String id) {
        return clientService.getClientById(id);
    }

    @Override
    public Client updateClient(String id ,Client client) {
        return clientService.updateClient(id,client);
    }

    @Override
    public void deleteClient(String id) {
        clientService.deleteClient(id);
    }

    @Override
    public List<Project> getProjectsByClientId(String clientId) {
        return clientService.getProjectsByClientId(clientId);
    }

//==============DTO==============

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