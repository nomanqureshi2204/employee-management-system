package com.noman.ems.admin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.service.EmployeeService;
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.service.ProjectService;
import com.noman.ems.client.entity.Client;
import com.noman.ems.client.service.ClientService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    // ================= EMPLOYEE =================

    @Override
    public Employee addEmployee(Employee emp) {
        return employeeService.add(emp);
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
        return clientService.add(client);
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
    public Client updateClient(Client client) {
        return clientService.updateClient(client);
    }

    @Override
    public void deleteClient(String id) {
        clientService.deleteClient(id);
    }

    @Override
    public List<Project> getProjectsByClientId(String clientId) {
        return clientService.getProjectsByClientId(clientId);
    }
}