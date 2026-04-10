package com.noman.ems.project.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.noman.ems.client.entity.Client;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.util.IdGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
public class Project {
	// Project ID auto-generated like PROJECT-001
	@Id
	private String projectId;

	private String projectName;
	private LocalDate startDate;
	private LocalDate endDate;

	// many projects belong to one client
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	// one project can have multiple employees
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Employee> employees = new ArrayList<>();

	

	public Project() {
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
