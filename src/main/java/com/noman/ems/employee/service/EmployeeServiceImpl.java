package com.noman.ems.employee.service;

import com.noman.ems.util.IdGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noman.ems.employee.dto.EmployeeResponseDto;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;
import com.noman.ems.exception.ResourceNotFoundException;
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

    // ✅ ADD EMPLOYEE
    @Override
    public Employee add(Employee emp) {

        String lastId = employeeRepo.findTopByOrderByEmployeeIdDesc()
                .map(Employee::getEmployeeId)
                .orElse(null);

        emp.setEmployeeId(IdGenerator.generateEmployeeId(lastId));

        if (emp.getJoiningDate() == null) {
            emp.setJoiningDate(LocalDate.now());
        }

        // ✅ ROLE FIX
        emp.setRole("ROLE_EMPLOYEE");

        return employeeRepo.save(emp);
    }

    // ✅ GET ALL
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // ✅ GET BY ID
    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    // ✅ DELETE
    @Override
    public void deleteEmployee(String id) {
        if (!employeeRepo.existsById(id)) {
            throw new RuntimeException("Invalid Employee ID");
        }
        employeeRepo.deleteById(id);
    }

    // ✅ UPDATE
    @Override
    public Employee updateEmployee(String id, Employee emp) {

        Employee old = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        old.setName(emp.getName());
        old.setDept(emp.getDept());
        old.setPhone(emp.getPhone());

        if (emp.getProject() != null && emp.getProject().getProjectId() != null) {

            Project project = projectRepo.findById(emp.getProject().getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

            old.setProject(project);
        } else {
            old.setProject(null);
        }

        return employeeRepo.save(old);
    }

    // ✅ GET BY EMAIL
    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    // ✅ DATE RANGE
    @Override
    public List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end) {
        return employeeRepo.findByjoiningDateBetween(start, end);
    }

    // ✅ ASSIGN PROJECT
    @Override
    public Employee assignEmployeeToProject(String empId, String projectId) {

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        emp.setProject(project);

        return employeeRepo.save(emp);
    }

    // ✅ RELEASE PROJECT
    @Override
    public String releaseEmployeeFromProject(String empId, String projectId) {

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        emp.setProject(null);

        employeeRepo.save(emp);

        return "Project released from " + emp.getName();
    }

    // ✅ BENCH EMPLOYEES
    @Override
    public List<Employee> getBenchEmployee() {
        return employeeRepo.findByProjectIsNull();
    }

    // 🔥 LOGIN (FINAL FIXED)
    @Override
    public String login(String email, String password) {

        Employee emp = employeeRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        // 🔒 LOCK CHECK
        if (emp.getLockTime() != null) {
            if (emp.getLockTime().isAfter(LocalDateTime.now())) {
                throw new RuntimeException("Account locked! Try after 5 minutes");
            } else {
                emp.setFailedAttempts(0);
                emp.setLockTime(null);
                emp.setAccountLocked(false);
            }
        }

        // 🔑 PASSWORD CHECK
        if (passwordEncoder.matches(password, emp.getPassword())) {

            emp.setFailedAttempts(0);
            emp.setLockTime(null);
            emp.setAccountLocked(false);
            employeeRepo.save(emp);

            // ✅ ROLE FIX
            String role = emp.getRole();
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }

            // 🔥 AUTH SET
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            emp.getEmail(),
                            null,
                            Collections.singleton(new SimpleGrantedAuthority(role))
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

            return "Employee Login Successful";

        } else {

            int attempts = emp.getFailedAttempts() + 1;
            emp.setFailedAttempts(attempts);

            if (attempts >= 5) {
                emp.setLockTime(LocalDateTime.now().plusMinutes(5));
                emp.setAccountLocked(true);
            }

            employeeRepo.save(emp);

            throw new RuntimeException("Invalid password. Attempts: " + attempts);
        }
    }

    // ✅ DTO CONVERTER
    private EmployeeResponseDto convertToDto(Employee emp) {

        EmployeeResponseDto dto = new EmployeeResponseDto();

        dto.setEmployeeId(emp.getEmployeeId());
        dto.setName(emp.getName());
        dto.setDept(emp.getDept());
        dto.setEmail(emp.getEmail());
        dto.setPhone(emp.getPhone());

        if (emp.getProject() != null) {
            dto.setProjectId(emp.getProject().getProjectId());
        }

        return dto;
    }

    // ✅ DTO METHODS
    @Override
    public List<EmployeeResponseDto> getAllEmployeesDto() {
        return employeeRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
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
        return employeeRepo.findByProjectIsNull()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesByDateRangeDto(LocalDate start, LocalDate end) {
        return employeeRepo.findByjoiningDateBetween(start, end)
                .stream()
                .map(this::convertToDto)
                .toList();
    }
}