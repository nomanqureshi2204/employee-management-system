package com.noman.ems.admin.service;

import java.time.LocalDate;
import java.util.List;

import com.noman.ems.employee.entity.Employee;
import com.noman.ems.project.entity.Project;
import com.noman.ems.client.entity.Client;

public interface AdminService {

    // ================= EMPLOYEE =================
    Employee addEmployee(Employee emp);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id);

    Employee getEmployeeByEmail(String email);

    List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end);

    Employee updateEmployee(String id, Employee emp);

    void deleteEmployee(String id);

    Employee assignEmployeeToProject(String empId, String projectId);

    String releaseEmployeeFromProject(String empId, String projectId);

    List<Employee> getBenchEmployees();

    // ================= PROJECT =================
    Project addProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(String id);

    Project updateProject(String id, Project project);

    void deleteProject(String id);

    List<Employee> getEmployeesByProjectId(String projectId);

    Client getClientByProjectId(String projectId);

    // ================= CLIENT =================
    Client addClient(Client client);

    List<Client> getAllClients();

    Client getClientById(String id);

    Client updateClient(Client client);

    void deleteClient(String id);

    List<Project> getProjectsByClientId(String clientId);
}