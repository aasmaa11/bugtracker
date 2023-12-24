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
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestProjectPersistence {
	
	@Autowired private ProjectRepository projectRepository;
	@Autowired private TicketRepository ticketRepository;
	@Autowired private DeveloperRepository developerRepository;
	@Autowired private ProjectManagerRepository projectManagerRepository;
	
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	projectRepository.deleteAll();
    	ticketRepository.deleteAll();
    	developerRepository.deleteAll();
    	projectManagerRepository.deleteAll();        
    }
        
     
    @Test
    public void testPersistAndLoadProject() {
    	String name = "ProjectName";
    	String description = "testing";
    	
    	Project project = new Project();
    	project.setName(name);
    	project.setDescription(description);
    	
    	projectRepository.save(project);
    	
    	int id = project.getId();


        project = null;

        // Load the object from the database
        project = projectRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
       
    }
    
    @Test
    @Transactional
    public void testAssignTicket() {
		  String title = "test title";
		  String description = "test description";
		  TicketPriority priority = TicketPriority.High;
		  TicketStatus status = TicketStatus.InProgress;
		  TicketType type = TicketType.Bug;
	    	
		  Ticket ticket = new Ticket();
		  ticket.setTitle(title);
		  ticket.setDescription(description);
		  ticket.setPriority(priority);
		  ticket.setStatus(status);
		  ticket.setType(type);
		  
		  ticketRepository.save(ticket);
    	int ticketId = ticket.getId();
    	Set<Ticket> tickets = new HashSet<Ticket>();
    	tickets.add(ticket);
    	
        // Setting primitave attributes for customerAccount
    	String name = "ProjectName";
    	String descriptionP = "testing";
    	
    	Project project = new Project();
    	project.setName(name);
    	project.setDescription(descriptionP);
    	project.setTickets(tickets);    	
    	projectRepository.save(project);
    	
    	int id = project.getId();

        project = null;

        // Load the object from the database
        project = projectRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(descriptionP, project.getDescription());

        assertEquals(1, project.getTickets().size());
        for (Ticket t : project.getTickets()) {
            if (t.getId() == ticketId) {
                assertEquals(title, t.getTitle());
                assertEquals(description, t.getDescription());
                assertEquals(priority, t.getPriority());
                assertEquals(status, t.getStatus());
                assertEquals(type, t.getType());
                break;
            }
        }
    }
    
    
    @Test
    @Transactional
    public void testAssignDeveloper() {
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        Developer developer = new Developer();
        developer.setUsername(username);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);

        // Save the object from the database
        developerRepository.save(developer);
        int developerId = developer.getId();
    	Set<Developer> developers = new HashSet<Developer>();
    	developers.add(developer);
    	
        // Setting primitave attributes for customerAccount
    	String name = "ProjectName";
    	String descriptionP = "testing";
    	
    	Project project = new Project();
    	project.setName(name);
    	project.setDescription(descriptionP);
    	project.setAssignedDevelopers(developers);
    	
    	projectRepository.save(project);
    	
    	int id = project.getId();

        project = null;

        // Load the object from the database
        project = projectRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(descriptionP, project.getDescription());

        assertEquals(1, project.getAssignedDevelopers().size());
        for (Developer d : project.getAssignedDevelopers()) {
            if (d.getId() == developerId) {
                assertEquals(username, d.getUsername());
                assertEquals(password, d.getPassword());
                assertEquals(email, d.getEmail());
                assertEquals(firstName, d.getFirstName());
                assertEquals(lastName, d.getLastName());
                break;
            }
        }
    }
    
    
    @Test
    @Transactional
    public void testAssignProjectManager() {
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
        // Setting primitave attributes for customerAccount
    	String name = "ProjectName";
    	String descriptionP = "testing";
    	
    	Project project = new Project();
    	project.setName(name);
    	project.setDescription(descriptionP);
    	project.setProjectManager(projectManager);
    	
    	projectRepository.save(project);
    	
    	int id = project.getId();

        project = null;

        // Load the object from the database
        project = projectRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(descriptionP, project.getDescription());
        assertEquals(username, project.getProjectManager().getUsername());
        assertEquals(password, project.getProjectManager().getPassword());
        assertEquals(email, project.getProjectManager().getEmail());
        assertEquals(firstName, project.getProjectManager().getFirstName());
        assertEquals(lastName, project.getProjectManager().getLastName());


    }
}
