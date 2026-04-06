package com.noman.ems.employee.service;

import java.util.List;

import com.noman.ems.employee.entity.Employee;


public interface EmployeeService {

    Employee save(Employee emp);
    
    List<Employee> getAll();
    
    Employee getById(String id);
    
    void delete(String id);
}





