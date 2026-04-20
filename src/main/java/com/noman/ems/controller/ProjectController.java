package com.noman.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.entity.Client;
import com.noman.ems.entity.Employee;
import com.noman.ems.dto.ProjectResponseDto;
import com.noman.ems.entity.Project;
import com.noman.ems.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping
    @Operation(summary = "Create Project")
    public Project addProject(@RequestBody Project project) {
        return service.add(project);
    }

    @GetMapping
    @Operation(summary = "Get All Projects")
    public List<ProjectResponseDto> getAllProjects() {
        return service.getAllProjectsDto();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Project By Id")
    public ProjectResponseDto getProjectById(@PathVariable String id) {
        return service.getProjectByIdDto(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Project")
    public Project updateProject(@PathVariable String id, @RequestBody Project project) {
        return service.updateProject(id,project);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Project")
    public String deleteProject(@PathVariable String id) {
        service.deleteProject(id);
        return "Project with ID " + id + " deleted successfully!";
    }
	
    @GetMapping("/{projectId}/client")
    @Operation(summary = "Get Client By Proejct Id")
	public Client getClientByProject(@PathVariable String projectId) {
		return service.getClientByProjectId(projectId);
	}
    
    @GetMapping ("/{projectId}/employees")
    @Operation(summary = "Get Employees By Proejct Id")
    public List<Employee> getEmployeesByProjectId(@PathVariable String projectId){
    	return service.getEmployeesByProjectId(projectId);
    }
    
    
}
    






