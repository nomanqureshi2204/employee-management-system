package com.noman.ems.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.service.AdminService;
import com.noman.ems.dto.EmployeeResponseDto;
import com.noman.ems.entity.Employee;
import com.noman.ems.entity.Project;
import com.noman.ems.entity.Client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    // ================= EMPLOYEE =================

    @Tag(name = "Employee APIs", description = "All Employee Operations")
    @Operation(summary = "Add Employee")
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee emp) {
        return service.addEmployee(emp);
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Get All Employees")
    @GetMapping("/employees")
    public List<EmployeeResponseDto> getAll() {
        return service.getAllEmployeesDto();
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Get Employee By ID")
    @GetMapping("/employee/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable String id) {
        return service.getEmployeeByIdDto(id);
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Get Employee By Email")
    @GetMapping("/employee/email")
    public EmployeeResponseDto getEmployeeByEmail(@RequestParam String email) {
        return service.getEmployeeByEmailDto(email);
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Get Employees By Date Range")
    @GetMapping("/employee/date-range")
    public List<EmployeeResponseDto> getEmployeesByDateRange(
            @RequestParam String start,
            @RequestParam String end) {

        return service.getEmployeesByDateRangeDto(
                LocalDate.parse(start),
                LocalDate.parse(end)
        );
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Update Employee")
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable String id,
                                   @RequestBody Employee emp) {
        return service.updateEmployee(id, emp);
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Delete Employee")
    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable String id) {
        service.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Assign Employee To Project")
    @PutMapping("/assign/{empId}/{projectId}")
    public Employee assignEmployee(@PathVariable String empId,
                                   @PathVariable String projectId) {
        return service.assignEmployeeToProject(empId, projectId);
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Release Employee From Project")
    @PutMapping("/release/{empId}/{projectId}")
    public String releaseEmployee(@PathVariable String empId,
                                  @PathVariable String projectId) {
        return service.releaseEmployeeFromProject(empId, projectId);
    }

    @Tag(name = "Employee APIs")
    @Operation(summary = "Get Bench Employees")
    @GetMapping("/bench")
    public List<EmployeeResponseDto> getBenchEmployees() {
        return service.getBenchEmployeeDto();
    }


    // ================= PROJECT =================

    @Tag(name = "Project APIs", description = "All Project Operations")
    @Operation(summary = "Add Project")
    @PostMapping("/project")
    public Project addProject(@RequestBody Project project) {
        return service.addProject(project);
    }

    @Tag(name = "Project APIs")
    @Operation(summary = "Get All Projects")
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return service.getAllProjects();
    }

    @Tag(name = "Project APIs")
    @Operation(summary = "Get Project By ID")
    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable String id) {
        return service.getProjectById(id);
    }

    @Tag(name = "Project APIs")
    @Operation(summary = "Update Project")
    @PutMapping("/project/{id}")
    public Project updateProject(@PathVariable String id,
                                 @RequestBody Project project) {
        return service.updateProject(id, project);
    }

    @Tag(name = "Project APIs")
    @Operation(summary = "Delete Project")
    @DeleteMapping("/project/{id}")
    public String deleteProject(@PathVariable String id) {
        service.deleteProject(id);
        return "Project deleted successfully";
    }

    @Tag(name = "Project APIs")
    @Operation(summary = "Get Employees By Project")
    @GetMapping("/project/{id}/employees")
    public List<Employee> getEmployeesByProject(@PathVariable String id) {
        return service.getEmployeesByProjectId(id);
    }

    @Tag(name = "Project APIs")
    @Operation(summary = "Get Client By Project")
    @GetMapping("/project/{id}/client")
    public Client getClientByProject(@PathVariable String id) {
        return service.getClientByProjectId(id);
    }


    // ================= CLIENT =================

    @Tag(name = "Client APIs", description = "All Client Operations")
    @Operation(summary = "Add Client")
    @PostMapping("/client")
    public Client addClient(@RequestBody Client client) {
        return service.addClient(client);
    }

    @Tag(name = "Client APIs")
    @Operation(summary = "Get All Clients")
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return service.getAllClients();
    }

    @Tag(name = "Client APIs")
    @Operation(summary = "Get Client By ID")
    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable String id) {
        return service.getClientById(id);
    }

    @Tag(name = "Client APIs")
    @Operation(summary = "Update Client")
    @PutMapping("/client")
    public Client updateClient(@RequestBody Client client) {
        return service.updateClient(client);
    }

    @Tag(name = "Client APIs")
    @Operation(summary = "Delete Client")
    @DeleteMapping("/client/{id}")
    public String deleteClient(@PathVariable String id) {
        service.deleteClient(id);
        return "Client deleted successfully";
    }

    @Tag(name = "Client APIs")
    @Operation(summary = "Get Projects By Client")
    @GetMapping("/client/{id}/projects")
    public List<Project> getProjectsByClient(@PathVariable String id) {
        return service.getProjectsByClientId(id);
    }
}