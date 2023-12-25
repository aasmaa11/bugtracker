package com.bugtracker.SpringBootRestApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.bugtracker.SpringBootRestApp.dto.*;
import com.bugtracker.SpringBootRestApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.service.ProjectService;

@CrossOrigin(origins = "*")
@RestController
public class ProjectController {
    @Autowired private ProjectService projectService;
    
    @Autowired private DtoConverterRestController converter;
    
    /*
     * Create project								DONE
     * Add members to project						DONE
     * Get all projects assigned to project manager	DONE
     * Get all projects assigned to developer 		DONE
     * Get all projects 							DONE
     * Modify project								DONE
     * Assign project manager to project			DONE
     * Get project by id							DONE
     * Delete project 								DONE
     * 
     * */
    
    @GetMapping(value = {"/projects", "/projects/"})
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects().stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/project/{id}", "/project/{id}/"})
    public ProjectDto getProject(@PathVariable("id") String id) {
        return converter.convertToDto(projectService.getProject(Integer.parseInt(id)));
    }
    
    @GetMapping(value = {"/developer/projects/{username}", "/developer/projects/{username}"})
    public List<ProjectDto> getAllProjectsForDeveloper(@PathVariable("username") String username) {
        return projectService.getAllProjectsForDeveloper(username).stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/projectmanager/projects/{username}", "/projectmanager/projects/{username}"})
    public List<ProjectDto> getAllProjectsForProjectManager(@PathVariable("username") String username) {
        return projectService.getAllProjectsForProjectManager(username).stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    
    @PostMapping(value = {"/project/create", "/project/create/"})
    public ProjectDto createProject(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String projectManagerUsername)
            throws IllegalArgumentException {
        Project project = projectService.createProject(name, description, projectManagerUsername, null);
        return converter.convertToDto(project);
    }
    
    @PostMapping(value = {"/project/modify", "/project/modify/"})
    public ProjectDto modifyProject(
    		@RequestParam int projectId,
            @RequestParam String name,
            @RequestParam String description)
            throws IllegalArgumentException {
    	Project project = projectService.modifyProject(projectId, name, description);
        return converter.convertToDto(project);
    }    
    
    @PostMapping(value = {"/project/addDev/{username}", "/project/addDev/{username}/"})
    public ProjectDto addDeveloperToProject(
    		@PathVariable("username") String username,
            @RequestParam int projectId)
            throws IllegalArgumentException {
    	Project project = projectService.addDeveloperToProject(projectId, username);
        return converter.convertToDto(project);
    }
    
    @PostMapping(value = {"/project/assignPm/{username}", "/project/assignPm/{username}/"})
    public ProjectDto assignProjectManagerToProject(
    		@PathVariable("username") String username,
            @RequestParam int projectId)
            throws IllegalArgumentException {
    	Project project = projectService.assignProjectManagerToProject(projectId, username);
        return converter.convertToDto(project);
    }
    
    @PostMapping(value = {"/project/delete/{id}", "/project/delete/{id}/"})
    public ProjectDto deleteProject(@PathVariable("id") String id)
            throws IllegalArgumentException {
        return converter.convertToDto(projectService.deleteProject(Integer.parseInt(id)));
    }
}
