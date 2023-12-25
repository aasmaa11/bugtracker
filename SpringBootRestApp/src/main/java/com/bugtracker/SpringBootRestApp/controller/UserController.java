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

import com.bugtracker.SpringBootRestApp.service.UserService;
import com.bugtracker.SpringBootRestApp.service.UserService.UserRole;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
    @Autowired private UserService userService;
    
    @Autowired private DtoConverterRestController converter;
    
    /*
     * Create admin				DONE
     * Create project manager	DONE
     * Create developer			DONE
     * Get admin				DONE
     * Get project manager		DONE
     * Get developer			DONE
     * Get all users			DONE
     * Get user role			DONE
     * Change user role			DONE
     * Delete admin				DONE
     * Delete project manager	DONE
     * Delete developer			DONE
     * Modify user				DONE
     * 
     * 
     * 
     * */
    
    @PostMapping(value = {"/admin/create", "/admin/create/"})
    public AdminDto createAdmin(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName)
            throws IllegalArgumentException {
    	Admin admin = userService.createAdmin(username, email, password, firstName, lastName);
        return converter.convertToDto(admin);
    }
    
    @PostMapping(value = {"/projectmanager/create", "/projectmanager/create/"})
    public ProjectManagerDto createProjectManager(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName)
            throws IllegalArgumentException {
    	ProjectManager projectManager = userService.createProjectManager(username, email, password, firstName, lastName);
        return converter.convertToDto(projectManager);
    }
    
    @PostMapping(value = {"/developer/create", "/developer/create/"})
    public DeveloperDto createDeveloper(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName)
            throws IllegalArgumentException {
    	Developer developer = userService.createDeveloper(username, email, password, firstName, lastName);
        return converter.convertToDto(developer);
    }
    
    @GetMapping(value = {"/admin/{username}", "/project/{username}/"})
    public AdminDto getAdmin(@PathVariable("username") String username) {
        return converter.convertToDto(userService.getAdmin(username));
    }
    
    @GetMapping(value = {"/projectmanager/{username}", "/projectmanager/{username}/"})
    public ProjectManagerDto getProjectManager(@PathVariable("username") String username) {
        return converter.convertToDto(userService.getProjectManager(username));
    }
    
    @GetMapping(value = {"/developer/{username}", "/developer/{username}/"})
    public DeveloperDto getDeveloper(@PathVariable("username") String username) {
        return converter.convertToDto(userService.getDeveloper(username));
    }
    
    @GetMapping(value = {"/users", "/users/"})
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/admin/delete/{username}", "/project/delete/{username}/"})
    public AdminDto deleteAdmin(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return converter.convertToDto(userService.deleteAdmin(username));
    }
    
    @GetMapping(value = {"/projectmanager/delete/{username}", "/projectmanager/delete/{username}/"})
    public ProjectManagerDto deleteProjectManager(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return converter.convertToDto(userService.deleteProjectManager(username));
    }
    
    @GetMapping(value = {"/developer/delete/{username}", "/developer/delete/{username}/"})
    public DeveloperDto deleteDeveloper(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return converter.convertToDto(userService.deleteDeveloper(username));
    }
    
    @GetMapping(value = {"/user/role/{username}", "/user/role/{username}/"})
    public UserRole getUserRole(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return userService.getUserRole(username);
    }
    
    
    @PostMapping(value = {"/user/modify/{username}", "/user/modify/{username}/"})
    public UserDto modifyUser(
    		@PathVariable("username") String username,
            @RequestParam String newUsername,
            @RequestParam String newEmail,
            @RequestParam String newPassword,
            @RequestParam String newFirstName,
            @RequestParam String newLastName)
            throws IllegalArgumentException {
    	UserAccount user = userService.modifyUser(username, newUsername, newEmail, newPassword, newFirstName, newLastName);
        return converter.convertToDto(user);
    }  
    
    @PostMapping(value = {"/user/modifyRole/{username}", "/user/modifyRole/{username}/"})
    public UserDto changeUserRole(
    		@PathVariable("username") String username,
            @RequestParam String userRole)
            throws IllegalArgumentException {
    	UserRole role = null;
    	if(userRole.equals("Admin")) {
    		role = UserRole.Admin;
    	}else if(userRole.equals("ProjectManager")) {
    		role = UserRole.ProjectManager;
    	}else if(userRole.equals("Developer")) {
    		role = UserRole.Developer;
    	}else {
    		return converter.convertToDto(userService.getUser(username));
    	}
    	UserAccount user = userService.changeUserRole(username, role);
        return converter.convertToDto(user);
    }  


}
