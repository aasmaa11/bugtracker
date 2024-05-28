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
public class TestDeveloperPersistence {
	@Autowired private ProjectManagerRepository projectManagerRepository;
	@Autowired private DeveloperRepository developerRepository;
	
	@Autowired private TicketRepository ticketRepository;
	
	@Autowired private ProjectRepository projectRepository;
	
        
     
    @Test
    @Transactional
    public void testPersistAndLoadDeveloper() {
        // Setting primitave attributes for customerAccount
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

        developer = null;

        // Load the object from the database
        developer = developerRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(developer);
        assertEquals(username, developer.getUsername());
        assertEquals(password, developer.getPassword());
        assertEquals(email, developer.getEmail());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());
        
        developerRepository.delete(developer);
       
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

        Developer developer = new Developer();
        developer.setUsername(username);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setAssignedTickets(tickets);

        // Save the object from the database
        developerRepository.save(developer);

        developer = null;

        // Load the object from the database
        developer = developerRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(developer);
        assertEquals(username, developer.getUsername());
        assertEquals(password, developer.getPassword());
        assertEquals(email, developer.getEmail());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());

        assertEquals(1, developer.getAssignedTickets().size());
        for (Ticket t : developer.getAssignedTickets()) {
            if (t.getId() == ticketId) {
                assertEquals(title, t.getTitle());
                assertEquals(description, t.getDescription());
                assertEquals(priority, t.getPriority());
                assertEquals(status, t.getStatus());
                assertEquals(type, t.getType());
                break;
            }
        }
        ticketRepository.delete(ticket);
        developerRepository.delete(developer);
    }
    
 
}
