package com.noman.ems.dto;

import java.util.List;

public class ClientResponseDto {
	
	private String clientId;
	private String clientName;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String relationshipDate;
	
	private List<String>projectIds;   // only IDs
	private List<String>contactPersons; // simple view 
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
	public String getRelationshipDate() {
		return relationshipDate;
	}
	public void setRelationshipDate(String relationshipDate) {
		this.relationshipDate = relationshipDate;
	}
	public List<String> getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}
	public List<String> getContactPersons() {
		return contactPersons;
	}
	public void setContactPersons(List<String> contactNames) {
		this.contactPersons = contactNames;
	}
	
	
}	








