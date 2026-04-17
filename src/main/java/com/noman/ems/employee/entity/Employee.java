package com.noman.ems.employee.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noman.ems.project.entity.Project;
import com.noman.ems.user.entity.User;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private String employeeId;

    private String name;
    private String dept;

    private String phone;

    private LocalDate joiningDate;

    // 🔥 NEW: User mapping
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔗 Many employees → one project
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"employees"})
    private Project project;

    // ======================= GETTERS & SETTERS =======================

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}