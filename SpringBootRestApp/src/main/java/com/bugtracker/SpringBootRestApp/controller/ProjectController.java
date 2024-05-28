package com.bugtracker.SpringBootRestApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.bugtracker.SpringBootRestApp.controller.DtoConverterToLoad.Loads;
import com.bugtracker.SpringBootRestApp.dto.*;
import com.bugtracker.SpringBootRestApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.service.ProjectService;

// https://www.bezkoder.com/spring-boot-vue-js-authentication-jwt-spring-security/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
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
    
    
    private DtoConverterToLoad projectLoads =
            new DtoConverterToLoad()
                    .put(Loads.ProjectManager, null)
                    .put(Loads.AssignedDevelopers, null);
    
    @GetMapping(value = {"/projects", "/projects/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER') or hasAuthority('ROLE_DEVELOPER')")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects().stream()
                .map(p -> converter.convertToDto(p, projectLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/project/{id}", "/project/{id}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER') or hasAuthority('ROLE_DEVELOPER')")
    public ProjectDto getProject(@PathVariable("id") String id) {
        return converter.convertToDto(projectService.getProject(Integer.parseInt(id)), projectLoads);
    }
    
    
    @GetMapping(value = {"/developer/projects/{username}", "/developer/projects/{username}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER') or hasAuthority('ROLE_DEVELOPER')")
    public List<ProjectDto> getAllProjectsForDeveloper(@PathVariable("username") String username) {
        return projectService.getAllProjectsForDeveloper(username).stream()
                .map(p -> converter.convertToDto(p, projectLoads))
                .collect(Collectors.toList());
    }
    
    
    
    @GetMapping(value = {"/projectmanager/projects/{username}", "/projectmanager/projects/{username}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public List<ProjectDto> getAllProjectsForProjectManager(@PathVariable("username") String username) {
        return projectService.getAllProjectsForProjectManager(username).stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    @PostMapping(value = {"/project/create", "/project/create/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public ProjectDto createProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("projectManagerUsername") String projectManagerUsername)
            throws IllegalArgumentException {
        Project project = projectService.createProject(name, description, projectManagerUsername, null);
        return converter.convertToDto(project, projectLoads);
    }
    
    @PostMapping(value = {"/project/modify", "/project/modify/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public ProjectDto modifyProject(
    		@RequestParam("projectId") String projectId,
            @RequestParam("name") String name,
            @RequestParam("description") String description)
            throws IllegalArgumentException {
    	Project project = projectService.modifyProject(Integer.parseInt(projectId), name, description);
        return converter.convertToDto(project);
    }    
    
    @PostMapping(value = {"/project/addDev/{username}", "/project/addDev/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public ProjectDto addDeveloperToProject(
    		@PathVariable("username") String username,
            @RequestParam("projectId") int projectId)
            throws IllegalArgumentException {
    	Project project = projectService.addDeveloperToProject(projectId, username);
        return converter.convertToDto(project, projectLoads);
    }
    
    @PostMapping(value = {"/project/setDevs/{id}", "/project/addDevs/{id}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public ProjectDto setDevelopersToProject(
    		@PathVariable("id") String id,
            @RequestParam("devUsernames") List<String> devUsernames)
            throws IllegalArgumentException {
    	Project project = null;
    	project = projectService.setDevelopersToProject(Integer.parseInt(id), devUsernames);
    	
        return converter.convertToDto(project, projectLoads);
    }
    
    @PostMapping(value = {"/project/assignPm/{username}", "/project/assignPm/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public ProjectDto assignProjectManagerToProject(
    		@PathVariable("username") String username,
            @RequestParam("projectId") String projectId)
            throws IllegalArgumentException {
    	Project project = projectService.assignProjectManagerToProject(Integer.parseInt(projectId), username);
        return converter.convertToDto(project, projectLoads);
    }
    
    @PostMapping(value = {"/project/delete/{id}", "/project/delete/{id}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public ProjectDto deleteProject(@PathVariable("id") String id)
            throws IllegalArgumentException {
    	
    	// delete all tickets assigned to project
    	projectService.deleteTicketsForProject(Integer.parseInt(id));
    	Project project = projectService.deleteProject(Integer.parseInt(id));
        return converter.convertToDto(project);
    }
}
