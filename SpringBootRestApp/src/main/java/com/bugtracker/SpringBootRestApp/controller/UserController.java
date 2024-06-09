package com.bugtracker.SpringBootRestApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.bugtracker.SpringBootRestApp.dto.*;
import com.bugtracker.SpringBootRestApp.model.*;
import com.bugtracker.SpringBootRestApp.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.service.AuthenticatedUserService;
import com.bugtracker.SpringBootRestApp.service.UserService;
import com.bugtracker.SpringBootRestApp.service.UserService.UserRole;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {
	
	@Autowired private  AuthenticatedUserService authenticatedUserService;
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
    /*
    @PostMapping(value = {"/admin/create", "/admin/create/"})
    public AdminDto createAdmin(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName)
            throws IllegalArgumentException {
    	Admin admin = userService.createAdmin(username, email, password, firstName, lastName);
        return converter.convertToDto(admin);
    }
    
    @PostMapping(value = {"/projectmanager/create", "/projectmanager/create/"})
    public ProjectManagerDto createProjectManager(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName)
            throws IllegalArgumentException {
    	ProjectManager projectManager = userService.createProjectManager(username, email, password, firstName, lastName);
        return converter.convertToDto(projectManager);
    }
    
    @PostMapping(value = {"/developer/create", "/developer/create/"})
    public DeveloperDto createDeveloper(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName)
            throws IllegalArgumentException {
    	Developer developer = userService.createDeveloper(username, email, password, firstName, lastName);
    	DeveloperDto dev = converter.convertToDto(developer);
    	System.out.println("DEVDTO ID");
    	System.out.println(dev.getId());
        return dev;
    }
    */
    @GetMapping(value = {"/admin/{username}", "/project/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AdminDto getAdmin(@PathVariable("username") String username) {
        return converter.convertToDto(userService.getAdmin(username));
    }
    
    @GetMapping(value = {"/projectmanager/{username}", "/projectmanager/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProjectManagerDto getProjectManager(@PathVariable("username") String username) {
        return converter.convertToDto(userService.getProjectManager(username));
    }
    
    @GetMapping(value = {"/developer/{username}", "/developer/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public DeveloperDto getDeveloper(@PathVariable("username") String username) {
        return converter.convertToDto(userService.getDeveloper(username));
    }
    
    @GetMapping(value = {"/user/{username}", "/user/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @authenticatedUserService.isMyPage(#username)")
    public UserDto getUser(@Param("username") @PathVariable("username") String username) {
        return converter.convertToDto(userService.getUser(username));
    }
    
    @GetMapping({"/users", "/users/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/developers", "/developers/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public List<DeveloperDto> getAllDevelopers() {
        return userService.getAllDevelopers().stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/projectmanagers", "/projectmanagers/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROJECTMANAGER')")
    public List<ProjectManagerDto> getAllProjectManagers() {
        return userService.getAllProjectManagers().stream()
                .map(p -> converter.convertToDto(p))
                .collect(Collectors.toList());
    }
    
    
    @PostMapping(value = {"/admin/delete/{username}", "/project/delete/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AdminDto deleteAdmin(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return converter.convertToDto(userService.deleteAdmin(username));
    }
    
    @PostMapping(value = {"/projectmanager/delete/{username}", "/projectmanager/delete/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProjectManagerDto deleteProjectManager(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return converter.convertToDto(userService.deleteProjectManager(username));
    }
    
    @PostMapping(value = {"/developer/delete/{username}", "/developer/delete/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public DeveloperDto deleteDeveloper(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return converter.convertToDto(userService.deleteDeveloper(username));
    }
    
    @GetMapping(value = {"/user/role/{username}", "/user/role/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserRole getUserRole(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return userService.getUserRole(username);
    }
    
    
    @PostMapping(value = {"/user/modify/{username}", "/user/modify/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @authenticatedUserService.isMyPage(#username)")
    public UserDto modifyUser(
    		@Param("username") @PathVariable("username") String username,
            @RequestParam("newUsername") String newUsername,
            @RequestParam("newEmail") String newEmail,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("newFirstName") String newFirstName,
            @RequestParam("newLastName") String newLastName)
            throws IllegalArgumentException {
    	UserAccount user = userService.modifyUser(username, newUsername, newEmail, newPassword, newFirstName, newLastName);
        return converter.convertToDto(user);
    }  
    
    @PostMapping(value = {"/user/modifyRole/{username}", "/user/modifyRole/{username}/"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDto changeUserRole(
    		@PathVariable("username") String username,
    		@RequestParam("userRole") String userRole)
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
