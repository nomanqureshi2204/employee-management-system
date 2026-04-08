package com.noman.ems.project.service;

import java.util.List;

import com.noman.ems.project.entity.Project;

public interface ProjectService {

	Project add(Project project);

	List<Project> getAllProjects();

	Project getProjectById(String id);

	Project updateProject(String id, Project project);

	void deleteProject(String id);
}
