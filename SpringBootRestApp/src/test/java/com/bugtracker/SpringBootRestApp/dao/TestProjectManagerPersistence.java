package com.bugtracker.SpringBootRestApp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestProjectManagerPersistence {
	@Autowired private ProjectManagerRepository projectManagerRepository;
	
	@Autowired private ProjectRepository projectRepository;
	
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	projectManagerRepository.deleteAll();
    	projectRepository.deleteAll();
        
    }
        
     
    @Test
    @Transactional
    public void testPersistAndLoadProjectManager() {
        // Setting primitave attributes for customerAccount
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        ProjectManager projectManager = new ProjectManager();
        projectManager.setUsername(username);
        projectManager.setPassword(password);
        projectManager.setEmail(email);
        projectManager.setFirstName(firstName);
        projectManager.setLastName(lastName);

        // Save the object from the database
        projectManagerRepository.save(projectManager);

        projectManager = null;

        // Load the object from the database
        projectManager = projectManagerRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(projectManager);
        assertEquals(username, projectManager.getUsername());
        assertEquals(password, projectManager.getPassword());
        assertEquals(email, projectManager.getEmail());
        assertEquals(firstName, projectManager.getFirstName());
        assertEquals(lastName, projectManager.getLastName());
       
    }
    
    @Test
    @Transactional
    public void testAssignProject() {
    	String name = "ProjectName";
    	String description = "testing";
    	
    	Project project = new Project();
    	project.setName(name);
    	project.setDescription(description);
    	int id = project.getId();
    	projectRepository.save(project);
    	
    	Set<Project> projects = new HashSet<Project>();
    	projects.add(project);
    	
        // Setting primitave attributes for customerAccount
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        ProjectManager projectManager = new ProjectManager();
        projectManager.setUsername(username);
        projectManager.setPassword(password);
        projectManager.setEmail(email);
        projectManager.setFirstName(firstName);
        projectManager.setLastName(lastName);
        projectManager.setProjects(projects);

        // Save the object from the database
        projectManagerRepository.save(projectManager);
        
        projectManager = null;

        // Load the object from the database
        projectManager = projectManagerRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(projectManager);
        assertEquals(username, projectManager.getUsername());
        assertEquals(password, projectManager.getPassword());
        assertEquals(email, projectManager.getEmail());
        assertEquals(firstName, projectManager.getFirstName());
        assertEquals(lastName, projectManager.getLastName());

        assertEquals(1, projectManager.getProjects().size());
        for (Project p : projectManager.getProjects()) {
            if (p.getId() == id) {
            	assertEquals(name, p.getName());
            	assertEquals(description, p.getDescription());
                break;
            }
        }
    }
}
