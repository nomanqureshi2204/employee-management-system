package com.noman.ems.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.client.entity.Client;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping
    public Project addProject(@RequestBody Project project) {
        return service.add(project);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return service.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        return service.getProjectById(id);
    }

    @PutMapping("{id}")
    public Project updateProject(@PathVariable String id, @RequestBody Project project) {
        return service.updateProject(id,project);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable String id) {
        service.deleteProject(id);
        return "Project with ID " + id + " deleted successfully!";
    }
	
    @GetMapping("project/{projectId}/client")
	public Client getClientByProject(@PathVariable String projectId) {
		return service.getClientByProjectId(projectId);
	}
    
    @GetMapping ("project/{projectId}/employees")
    public List<Employee> getEmployeesByProjectId(@PathVariable String projectId){
    	return service.getEmployeesByProjectId(projectId);
    }
}
    






