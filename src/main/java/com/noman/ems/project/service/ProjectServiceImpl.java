package com.noman.ems.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repo;

    @Override
    public Project add(Project project) {
        return repo.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return repo.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Project updateProject(String id, Project project) {
        Project existing = repo.findById(id).orElse(null);

        if (existing != null) {
            existing.setProjectName(project.getProjectName());
            existing.setStartDate(project.getStartDate());
            existing.setEndDate(project.getEndDate());
            // abhi relation update nahi karenge
        }

        return repo.save(existing);
    }

    @Override
    public void deleteProject(String id) {
        repo.deleteById(id);
    }
}
