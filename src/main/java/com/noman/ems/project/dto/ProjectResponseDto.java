package com.noman.ems.project.dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponseDto {
	
	private String projectId;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;

    private String clientId;
    private List<String> employeeIds;
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List<String> getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(List<String> employeeIds) {
		this.employeeIds = employeeIds;
	}
    
    

}
