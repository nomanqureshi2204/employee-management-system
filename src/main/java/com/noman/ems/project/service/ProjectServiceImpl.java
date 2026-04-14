package com.noman.ems.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.exception.ResourceNotFoundException;
import com.noman.ems.project.dto.ProjectResponseDto;
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;
import com.noman.ems.util.IdGenerator;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	ClientRepository clientRepo;

	// add a new project with auto-genrated ID
	@Override
	public Project add(Project project) {
		// get last project ID from DB
		String lastId = projectRepo.findTopByOrderByProjectIdDesc().map(Project::getProjectId).orElse(null);
		// generate new project id
		String newId = IdGenerator.generateProjectId(lastId);

		// set new generated ID
		project.setProjectId(newId);

		// save project to DB
		return projectRepo.save(project);
	}

	@Override
	public List<Project> getAllProjects() {
		return projectRepo.findAll();
	}

	@Override
	public Project getProjectById(String id) {
		return projectRepo.findById(id).orElse(null);
	}

	// update project info (except projectId)
	@Override
	public Project updateProject(String id, Project project) {
		Project old = projectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

		old.setProjectName(project.getProjectName());
		old.setStartDate(project.getStartDate());
		old.setEndDate(project.getEndDate());

		if (project.getClient() != null && project.getClient().getClientId() != null) {
			String clientId = project.getClient().getClientId();

			Client client = clientRepo.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found"));

			old.setClient(client);

		}

		return projectRepo.save(old);

	}

	@Override
	public void deleteProject(String id) {
		if (!projectRepo.existsById(id)) {
			throw new RuntimeException("Invalid Project ID");
		}
		projectRepo.deleteById(id);

	}

	@Override
	public Client getClientByProjectId(String projectId) {
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

		if (project.getClient() == null) {
			throw new RuntimeException("No client assigned to this project");
		}

		return project.getClient();
	}

	@Override
	public List<Employee> getEmployeesByProjectId(String projectId) {
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

		if (project.getEmployees() == null) {
			throw new RuntimeException("No Employees assigned to this project");
		}

		return project.getEmployees();

	}

	private ProjectResponseDto convertToDto(Project project) {

		ProjectResponseDto dto = new ProjectResponseDto();

		dto.setProjectId(project.getProjectId());
		dto.setProjectName(project.getProjectName());
		dto.setStartDate(project.getStartDate());
		dto.setEndDate(project.getEndDate());

		// client
		if (project.getClient() != null) {
			dto.setClientId(project.getClient().getClientId());
		}

		// employees
		if (project.getEmployees() != null) {
			dto.setEmployeeIds(project.getEmployees().stream().map(emp -> emp.getEmployeeId()).toList());
		}

		return dto;
	}

	@Override
	public List<ProjectResponseDto> getAllProjectsDto() {
		return projectRepo.findAll().stream().map(this::convertToDto).toList();
	}

	@Override
	public ProjectResponseDto getProjectByIdDto(String id) {
		Project project = projectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

		return convertToDto(project);
	}
}
