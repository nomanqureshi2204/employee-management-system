package com.noman.ems.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;

public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository repo;

	@Override
	public Project save(Project project) {
		return repo.save(project);
	}

	@Override
	public List<Project> getAll() {
		return repo.findAll();
	}

	@Override
	public Project getById(String id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void delete(String id) {
		repo.deleteById(id);
	}

}
