package com.noman.ems.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noman.ems.project.entity.Project;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private String employeeId;

    // ✅ ROLE (must be ROLE_EMPLOYEE)
    private String role;

    private String name;
    private String dept;

    @Column(unique = true)
    private String email;

    private String phone;

    private LocalDate joiningDate;

    private String password;

    // 🔗 Many employees → one project
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"employees"})
    private Project project;

    // 🔐 Login fields
    private int failedAttempts;
    private boolean accountLocked;
    private LocalDateTime lockTime;

    // =======================
    // GETTERS & SETTERS
    // =======================

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }
}