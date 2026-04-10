package com.noman.ems.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;
import com.noman.ems.util.IdGenerator;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repo;

    // add a new project with auto-genrated ID
    @Override
    public Project add(Project project) {
    	// get last project ID from DB
    	String lastId = repo.findTopByOrderByProjectIdDesc()
    			.map(Project::getProjectId)
    			.orElse(null);
    	// generate new project id 
    	String newId = IdGenerator.generateProjectId(lastId);
    	
    	// set new generated ID 
    	project.setProjectId(newId);
    	
    	// save project to DB 
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
    
    //update project info (except projectId) 
    @Override
    public Project updateProject(Project project) {
        Optional<Project>existing = repo.findById(project.getProjectId());

        if (existing.isPresent()) {
            Project old = existing.get();
            old.setProjectName(project.getProjectName());
            old.setStartDate(project.getStartDate());
            old.setEndDate(project.getEndDate());
            old.setClient(project.getClient());
            old.setEmployees(project.getEmployees());
            
            return repo.save(old);
        }
        
        return null; //or throw exception 
        
    }

    @Override
    public void deleteProject(String id) {
        repo.deleteById(id);
    }
}
