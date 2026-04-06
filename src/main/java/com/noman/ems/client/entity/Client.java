package com.noman.ems.client.entity;

import java.time.LocalDate;
import java.util.List;

import com.noman.ems.project.entity.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Client {
	@Id
	private String clientId;
	
	private String companyName;
	
	private String email;
	
	private String password;
	
	private LocalDate relationshipDate;
	
	// One client → many projects
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	private List<Project>projects;
	
	 // One client → many contact persons
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	private List<ContactPerson>contactPersons;
}










