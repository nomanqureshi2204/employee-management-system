package com.noman.ems.client.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.noman.ems.project.entity.Project;
import com.noman.ems.util.IdGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
	
	
	 @Column(unique = true)
	 private String email;
//
//	 private String password;
//	 private int failedAttempts;
//	 private boolean accountLocked;
//	 private LocalDateTime lockTime;

	
	// One client can have  multiple projects
	@JsonIgnore
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	private List<Project>projects = new ArrayList<>();
	
	 // One client → many contact persons
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ContactPerson>contactPersons = new ArrayList<>();
	
	

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public LocalDate getRelationshipDate() {
		return relationshipDate;
	}

	public void setRelationshipDate(LocalDate relationshipDate) {
		this.relationshipDate = relationshipDate;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<ContactPerson> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}
	
	
}










