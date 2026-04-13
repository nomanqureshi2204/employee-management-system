package com.noman.ems.employee.service;

import java.time.LocalDate;
import java.util.List;

import com.noman.ems.employee.entity.Employee;


public interface EmployeeService {

    Employee add(Employee emp);
    
    List<Employee> getAllEmployees();
    
    Employee getEmployeeById(String id);
    
    Employee getEmployeeByEmail(String id);
    
    void deleteEmployee(String id);
    
    Employee updateEmployee(String id,Employee emp);
    
    List<Employee>getEmployeesByDateRange(LocalDate start,LocalDate end);
    
    Employee assignEmployeeToProject(String empId,String projectId);
    
    String releaseEmployeeFromProject(String empId,String projectId);
    
    List<Employee> getBenchEmployee();
    
    String login(String email,String password);
}





