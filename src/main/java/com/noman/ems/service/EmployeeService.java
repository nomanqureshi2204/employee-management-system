package com.noman.ems.service;

import java.time.LocalDate;
import java.util.List;

import com.noman.ems.dto.EmployeeResponseDto;
import com.noman.ems.entity.Employee;

public interface EmployeeService {

    // 🔹 CREATE
    Employee add(Employee emp);

    // 🔹 READ
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String id);
    Employee getEmployeeByEmail(String email);

    // 🔹 UPDATE
    Employee updateEmployee(String id, Employee emp);

    // 🔹 DELETE
    void deleteEmployee(String id);

    // 🔹 FILTER
    List<Employee> getEmployeesByDateRange(LocalDate start, LocalDate end);

    // 🔹 PROJECT OPERATIONS
    Employee assignEmployeeToProject(String empId, String projectId);
    String releaseEmployeeFromProject(String empId, String projectId);

    // 🔹 BENCH
    List<Employee> getBenchEmployee();

    // 🔹 DTO METHODS (API Response clean rakhne ke liye)
    List<EmployeeResponseDto> getAllEmployeesDto();
    EmployeeResponseDto getEmployeeByIdDto(String id);
    EmployeeResponseDto getEmployeeByEmailDto(String email);
    List<EmployeeResponseDto> getBenchEmployeeDto();
    List<EmployeeResponseDto> getEmployeesByDateRangeDto(LocalDate start, LocalDate end);
}