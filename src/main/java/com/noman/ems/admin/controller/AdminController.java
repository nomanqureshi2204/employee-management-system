package com.noman.ems.admin.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.admin.service.AdminService;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.project.entity.Project;
import com.noman.ems.client.entity.Client;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    // ================= EMPLOYEE =================

    // Add Employee
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee emp) {
        return service.addEmployee(emp);
    }

    // Get All Employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    // Get Employee by ID
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return service.getEmployeeById(id);
    }

    // Get Employee by Email
    @GetMapping("/employee/email")
    public Employee getEmployeeByEmail(@RequestParam String email) {
        return service.getEmployeeByEmail(email);
    }

    // Get Employees by Date Range
    @GetMapping("/employee/date-range")
    public List<Employee> getEmployeesByDateRange(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return service.getEmployeesByDateRange(startDate, endDate);
    }

    // Update Employee
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable String id,
                                   @RequestBody Employee emp) {
        return service.updateEmployee(id, emp);
    }

    // Delete Employee
    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable String id) {
        service.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    // Assign Employee to Project
    @PutMapping("/assign/{empId}/{projectId}")
    public Employee assignEmployee(@PathVariable String empId,
                                   @PathVariable String projectId) {
        return service.assignEmployeeToProject(empId, projectId);
    }

    // Release Employee from Project
    @PutMapping("/release/{empId}/{projectId}")
    public String releaseEmployee(@PathVariable String empId,
                                  @PathVariable String projectId) {
        return service.releaseEmployeeFromProject(empId, projectId);
    }

    // Get Bench Employees
    @GetMapping("/bench")
    public List<Employee> getBenchEmployees() {
        return service.getBenchEmployees();
    }

    // ================= PROJECT =================

    // Add Project
    @PostMapping("/project")
    public Project addProject(@RequestBody Project project) {
        return service.addProject(project);
    }

    // Get All Projects
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return service.getAllProjects();
    }

    // Get Project by ID
    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable String id) {
        return service.getProjectById(id);
    }

    // Update Project
    @PutMapping("/project/{id}")
    public Project updateProject(@PathVariable String id,
                                 @RequestBody Project project) {
        return service.updateProject(id, project);
    }

    // Delete Project
    @DeleteMapping("/project/{id}")
    public String deleteProject(@PathVariable String id) {
        service.deleteProject(id);
        return "Project deleted successfully";
    }

    // Get Employees by Project ID
    @GetMapping("/project/{id}/employees")
    public List<Employee> getEmployeesByProject(@PathVariable String id) {
        return service.getEmployeesByProjectId(id);
    }

    // Get Client by Project ID
    @GetMapping("/project/{id}/client")
    public Client getClientByProject(@PathVariable String id) {
        return service.getClientByProjectId(id);
    }

    // ================= CLIENT =================

    // Add Client
    @PostMapping("/client")
    public Client addClient(@RequestBody Client client) {
        return service.addClient(client);
    }

    // Get All Clients
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return service.getAllClients();
    }

    // Get Client by ID
    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable String id) {
        return service.getClientById(id);
    }

    // Update Client
    @PutMapping("/client")
    public Client updateClient(@RequestBody Client client) {
        return service.updateClient(client);
    }

    // Delete Client
    @DeleteMapping("/client/{id}")
    public String deleteClient(@PathVariable String id) {
        service.deleteClient(id);
        return "Client deleted successfully";
    }

    // Get Projects by Client ID
    @GetMapping("/client/{id}/projects")
    public List<Project> getProjectsByClient(@PathVariable String id) {
        return service.getProjectsByClientId(id);
    }
}