//package com.noman.ems.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import com.noman.ems.entity.Client;
//import com.noman.ems.entity.Employee;
//import com.noman.ems.dto.ProjectResponseDto;
//import com.noman.ems.entity.Project;
//import com.noman.ems.service.ProjectService;
//
//@RestController
//@RequestMapping("/projects")
//public class ProjectController {
//
//    @Autowired
//    private ProjectService service;
//
//    @PostMapping
//    public Project addProject(@RequestBody Project project) {
//        return service.add(project);
//    }
//
//    @GetMapping
//    public List<ProjectResponseDto> getAllProjects() {
//        return service.getAllProjectsDto();
//    }
//
//    @GetMapping("/{id}")
//    public ProjectResponseDto getProjectById(@PathVariable String id) {
//        return service.getProjectByIdDto(id);
//    }
//
//    @PutMapping("{id}")
//    public Project updateProject(@PathVariable String id, @RequestBody Project project) {
//        return service.updateProject(id,project);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteProject(@PathVariable String id) {
//        service.deleteProject(id);
//        return "Project with ID " + id + " deleted successfully!";
//    }
//	
//    @GetMapping("project/{projectId}/client")
//	public Client getClientByProject(@PathVariable String projectId) {
//		return service.getClientByProjectId(projectId);
//	}
//    
//    @GetMapping ("project/{projectId}/employees")
//    public List<Employee> getEmployeesByProjectId(@PathVariable String projectId){
//    	return service.getEmployeesByProjectId(projectId);
//    }
//    
//    
//}
//    
//
//
//
//
//
//
