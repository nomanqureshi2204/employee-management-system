package com.noman.ems.project.service;

import java.util.List;

import com.noman.ems.client.entity.Client;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.project.dto.ProjectResponseDto;
import com.noman.ems.project.entity.Project;

public interface ProjectService {

	Project add(Project project);

	List<Project> getAllProjects();

	Project getProjectById(String id);

	Project updateProject(String id, Project project);

	void deleteProject(String id);
	
	public Client getClientByProjectId(String projectId);
	
	public List<Employee>getEmployeesByProjectId(String projectId);
	
	List<ProjectResponseDto> getAllProjectsDto();
	ProjectResponseDto getProjectByIdDto(String id);
	
	
	
	
}










