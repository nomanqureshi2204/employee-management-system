package com.noman.ems.employee.service;

import java.util.List;

import com.noman.ems.employee.entity.Employee;


public interface EmployeeService {

    Employee add(Employee emp);
    
    List<Employee> getAllEmployees();
    
    Employee getEmployeeById(String id);
    
    void deleteEmployee(String id);
    
    Employee updateEmployee(String id,Employee emp);
}





