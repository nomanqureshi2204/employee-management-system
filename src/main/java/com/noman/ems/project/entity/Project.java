package com.noman.ems.project.entity;

import java.time.LocalDate;
import java.util.List;

import com.noman.ems.client.entity.Client;
import com.noman.ems.employee.entity.Employee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
	@Id
	private String projectId;
	
	private String projectName;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	//many projects belong to one client 
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	
	//one project has many employees 
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Employee>employees;
	
	
}









