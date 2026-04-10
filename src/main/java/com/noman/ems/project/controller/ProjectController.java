package com.noman.ems.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public Project updateProject(@PathVariable String id, @RequestBody Project project) {
        return service.updateProject(project);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable String id) {
        service.deleteProject(id);
        return "Project with ID " + id + " deleted successfully!";
    }
}