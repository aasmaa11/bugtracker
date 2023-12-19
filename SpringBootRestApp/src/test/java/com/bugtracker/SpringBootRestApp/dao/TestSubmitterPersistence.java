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

import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Submitter;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestSubmitterPersistence {
	@Autowired private SubmitterRepository submitterRepository;
	
	@Autowired private ProjectRepository projectRepository;
	
	@Autowired private TicketRepository ticketRepository;
	
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	submitterRepository.deleteAll();
    	projectRepository.deleteAll();
    	ticketRepository.deleteAll();
        
    }
        
     
    @Test
    @Transactional
    public void testPersistAndLoadSubmitter() {
        // Setting primitave attributes for customerAccount
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        Submitter submitter = new Submitter();
        submitter.setUsername(username);
        submitter.setPassword(password);
        submitter.setEmail(email);
        submitter.setFirstName(firstName);
        submitter.setLastName(lastName);

        // Save the object from the database
        submitterRepository.save(submitter);

        submitter = null;

        // Load the object from the database
        submitter = submitterRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(submitter);
        assertEquals(username, submitter.getUsername());
        assertEquals(password, submitter.getPassword());
        assertEquals(email, submitter.getEmail());
        assertEquals(firstName, submitter.getFirstName());
        assertEquals(lastName, submitter.getLastName());
       
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

        Submitter submitter = new Submitter();
        submitter.setUsername(username);
        submitter.setPassword(password);
        submitter.setEmail(email);
        submitter.setFirstName(firstName);
        submitter.setLastName(lastName);
        submitter.setProjects(projects);

        // Save the object from the database
        submitterRepository.save(submitter);
        
        submitter = null;

        // Load the object from the database
        submitter = submitterRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(submitter);
        assertEquals(username, submitter.getUsername());
        assertEquals(password, submitter.getPassword());
        assertEquals(email, submitter.getEmail());
        assertEquals(firstName, submitter.getFirstName());
        assertEquals(lastName, submitter.getLastName());

        assertEquals(1, submitter.getProjects().size());
        for (Project p : submitter.getProjects()) {
            if (p.getId() == id) {
            	assertEquals(name, p.getName());
            	assertEquals(description, p.getDescription());
                break;
            }
        }
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
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        Submitter submitter = new Submitter();
        submitter.setUsername(username);
        submitter.setPassword(password);
        submitter.setEmail(email);
        submitter.setFirstName(firstName);
        submitter.setLastName(lastName);
        submitter.setTickets(tickets);

        // Save the object from the database
        submitterRepository.save(submitter);
        
        submitter = null;

        // Load the object from the database
        submitter = submitterRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(submitter);
        assertEquals(username, submitter.getUsername());
        assertEquals(password, submitter.getPassword());
        assertEquals(email, submitter.getEmail());
        assertEquals(firstName, submitter.getFirstName());
        assertEquals(lastName, submitter.getLastName());

        assertEquals(1, submitter.getTickets().size());
        for (Ticket t : submitter.getTickets()) {
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
}
