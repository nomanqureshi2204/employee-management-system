package com.noman.ems.client.entity;

import java.time.LocalDate;
import java.util.List;

import com.noman.ems.project.entity.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="clients")
public class Client {
	// Client ID auto-generated like CLIENT-001
	@Id
	private String clientId;
	
	private String clientName;
	private LocalDate relationshipDate;
	

	
	// One client can have  multiple projects
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	private List<Project>projects;
	
	 // One client → many contact persons
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	private List<ContactPerson>contactPersons;
}










