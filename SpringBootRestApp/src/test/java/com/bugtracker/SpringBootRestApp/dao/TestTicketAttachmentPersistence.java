package com.bugtracker.SpringBootRestApp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTicketAttachmentPersistence {
	@Autowired private UserAccountRepository userAccountRepository;
	@Autowired private TicketAttachmentRepository ticketAttachmentRepository;
	
	@Autowired private TicketRepository ticketRepository;
	
	
        
     
    @Test
    @Transactional
    public void testPersistAndLoadTicketAttachment() {
        // Setting primitave attributes for customerAccount
        String file = "testName";
        String notes = "notes test";

        TicketAttachment ticketAttachment = new TicketAttachment();
        ticketAttachment.setFile(file);
        ticketAttachment.setNotes(notes);
        // Save the object from the database
        ticketAttachmentRepository.save(ticketAttachment);
        int id = ticketAttachment.getId();
        ticketAttachment = null;

        // Load the object from the database
        ticketAttachment = ticketAttachmentRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticketAttachment);
        assertEquals(file, ticketAttachment.getFile());
        assertEquals(notes, ticketAttachment.getNotes());
        
        ticketAttachmentRepository.delete(ticketAttachment);
       
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
        // Setting primitave attributes for customerAccount
        String file = "testName";
        String notes = "notes test";

        TicketAttachment ticketAttachment = new TicketAttachment();
        ticketAttachment.setFile(file);
        ticketAttachment.setNotes(notes);
        ticketAttachment.setTicket(ticket);
        // Save the object from the database
        ticketAttachmentRepository.save(ticketAttachment);
        int id = ticketAttachment.getId();
        ticketAttachment = null;

        // Load the object from the database
        ticketAttachment = ticketAttachmentRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticketAttachment);
        assertEquals(file, ticketAttachment.getFile());
        assertEquals(notes, ticketAttachment.getNotes());
        assertEquals(ticketId, ticketAttachment.getTicket().getId());
        assertEquals(title, ticketAttachment.getTicket().getTitle());
        assertEquals(description, ticketAttachment.getTicket().getDescription());
        assertEquals(priority, ticketAttachment.getTicket().getPriority());
        assertEquals(status, ticketAttachment.getTicket().getStatus());
        assertEquals(type, ticketAttachment.getTicket().getType());
        ticketRepository.delete(ticket);
        ticketAttachmentRepository.delete(ticketAttachment);
    }
    
    @Test
    @Transactional
    public void testAssignCreator() {
        // Setting primitave attributes for customerAccount
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        UserAccount developer = new Developer();
        developer.setUsername(username);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);

        // Save the object from the database
        userAccountRepository.save(developer);
        // Setting primitave attributes for customerAccount
        String file = "testName";
        String notes = "notes test";

        TicketAttachment ticketAttachment = new TicketAttachment();
        ticketAttachment.setFile(file);
        ticketAttachment.setNotes(notes);
        ticketAttachment.setCreator(developer);
        // Save the object from the database
        ticketAttachmentRepository.save(ticketAttachment);
        int id = ticketAttachment.getId();
        ticketAttachment = null;

        // Load the object from the database
        ticketAttachment = ticketAttachmentRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticketAttachment);
        assertEquals(file, ticketAttachment.getFile());
        assertEquals(notes, ticketAttachment.getNotes());
        assertEquals(username, ticketAttachment.getCreator().getUsername());
        assertEquals(password, ticketAttachment.getCreator().getPassword());
        assertEquals(email, ticketAttachment.getCreator().getEmail());
        assertEquals(firstName, ticketAttachment.getCreator().getFirstName());
        assertEquals(lastName, ticketAttachment.getCreator().getLastName());
        userAccountRepository.delete(developer);
        ticketAttachmentRepository.delete(ticketAttachment);
       
    }
}
