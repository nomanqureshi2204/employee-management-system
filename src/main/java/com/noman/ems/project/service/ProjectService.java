package com.noman.ems.project.service;

import java.util.List;

import com.noman.ems.project.entity.Project;

public interface ProjectService {
	
	Project save(Project project);
	List<Project>getAll();
	Project getById(String id);
	void delete(String id);
	
}
