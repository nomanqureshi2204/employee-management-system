package com.noman.ems.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.noman.ems.project.entity.Project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employees")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	@Id
	private String employeeId;
	
	@Column(name="employee_name",nullable = false)
	private String name;
	
	@Column(name="employee_email",nullable = false,unique = true)
	private String email;
	
	@Column(name="employee_phone",nullable = false)
	private String phone;
	
	@Column(name = "employee_dept", nullable = false)
    private String department;
	
	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;
	
	private String password;
	
	private int failedAttempts;
	
	private LocalDateTime accountLockedUntil;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public LocalDateTime getAccountLockedUntil() {
		return accountLockedUntil;
	}

	public void setAccountLockedUntil(LocalDateTime accountLockedUntil) {
		this.accountLockedUntil = accountLockedUntil;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", department=" + department + ", dateOfJoining=" + dateOfJoining + ", password=" + password
				+ ", failedAttempts=" + failedAttempts + ", accountLockedUntil=" + accountLockedUntil + ", project="
				+ project + "]";
	}

	
	
	
	
	
	
	
}









