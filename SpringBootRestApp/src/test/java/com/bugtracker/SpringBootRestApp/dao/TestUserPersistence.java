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

import com.bugtracker.SpringBootRestApp.model.Comment;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;
import com.bugtracker.SpringBootRestApp.model.UserAccount;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserPersistence {
	@Autowired private UserAccountRepository userAccountRepository;
	
	@Autowired private CommentRepository commentRepository;
	
	@Autowired private TicketRepository ticketRepository;
	@Autowired private TicketAttachmentRepository ticketAttachmentRepository;
	
        
     
    @Test
    @Transactional
    public void testPersistAndLoadDeveloper() {
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

        developer = null;

        // Load the object from the database
        developer = userAccountRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(developer);
        assertEquals(username, developer.getUsername());
        assertEquals(password, developer.getPassword());
        assertEquals(email, developer.getEmail());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());
        userAccountRepository.delete(developer);
    }
    
    @Test
    @Transactional
    public void testAssignComment() {
        // Setting primitive attributes for customerAccount
        String message = "message test";

        Comment comment = new Comment();
        comment.setMessage(message);
        
        // Save the object from the database
        commentRepository.save(comment);
        int id = comment.getId();
    	Set<Comment> comments = new HashSet<Comment>();
    	comments.add(comment);
    	
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
        developer.setComments(comments);

        // Save the object from the database
        userAccountRepository.save(developer);

        developer = null;

        // Load the object from the database
        developer = userAccountRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(developer);
        assertEquals(username, developer.getUsername());
        assertEquals(password, developer.getPassword());
        assertEquals(email, developer.getEmail());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());

        assertEquals(1, developer.getComments().size());
        for (Comment c : developer.getComments()) {
            if (c.getId() == id) {
            	assertEquals(message, c.getMessage());
                break;
            }
        }
        
        commentRepository.delete(comment);
        userAccountRepository.delete(developer);
    }
    
    @Test
    @Transactional
    public void testAssignTicket() {
        // Setting primitive attributes for customerAccount
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
		  int id = ticket.getId();
    	Set<Ticket> tickets = new HashSet<Ticket>();
    	tickets.add(ticket);
    	
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
        developer.setCreatedTickets(tickets);

        // Save the object from the database
        userAccountRepository.save(developer);

        developer = null;

        // Load the object from the database
        developer = userAccountRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(developer);
        assertEquals(username, developer.getUsername());
        assertEquals(password, developer.getPassword());
        assertEquals(email, developer.getEmail());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());

        assertEquals(1, developer.getCreatedTickets().size());
        for (Ticket t : developer.getCreatedTickets()) {
            if (t.getId() == id) {
                assertEquals(title, t.getTitle());
                assertEquals(description, t.getDescription());
                assertEquals(priority, t.getPriority());
                assertEquals(status, t.getStatus());
                assertEquals(type, t.getType());
                break;
            }
        }
        
        ticketRepository.delete(ticket);
        userAccountRepository.delete(developer);
    }
    
    @Test
    @Transactional
    public void testAssignTicketAttachment() {
        String file = "testName";
        String notes = "notes test";

        TicketAttachment ticketAttachment = new TicketAttachment();
        ticketAttachment.setFile(file);
        ticketAttachment.setNotes(notes);
        // Save the object from the database
        ticketAttachmentRepository.save(ticketAttachment);
        int id = ticketAttachment.getId();
    	Set<TicketAttachment> ticketAttachments = new HashSet<TicketAttachment>();
    	ticketAttachments.add(ticketAttachment);
    	
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
        developer.setTicketAttachments(ticketAttachments);
        // Save the object from the database
        userAccountRepository.save(developer);

        developer = null;

        // Load the object from the database
        developer = userAccountRepository.findByUsername(username);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(developer);
        assertEquals(username, developer.getUsername());
        assertEquals(password, developer.getPassword());
        assertEquals(email, developer.getEmail());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());

        assertEquals(1, developer.getTicketAttachments().size());
        for (TicketAttachment t : developer.getTicketAttachments()) {
            if (t.getId() == id) {
            	 assertEquals(file, t.getFile());
                 assertEquals(notes, t.getNotes());
                break;
            }
        }
        
        ticketAttachmentRepository.delete(ticketAttachment);
        userAccountRepository.delete(developer);
    }
}
