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

import com.bugtracker.SpringBootRestApp.dao.CommentRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Comment;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCommentPersistence {
	
	@Autowired private CommentRepository commentRepository;
	
	@Autowired private TicketRepository ticketRepository;
	@Autowired private UserAccountRepository userAccountRepository;
	
	
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	commentRepository.deleteAll();
    	ticketRepository.deleteAll();
    	
    	userAccountRepository.deleteAll();
    	
    }
        
     
    @Test
    @Transactional
    public void testPersistAndLoadComment() {
        // Setting primitave attributes for customerAccount
        String message = "message test";

        Comment comment = new Comment();
        comment.setMessage(message);
        
        // Save the object from the database
        commentRepository.save(comment);
        int id = comment.getId();
        comment = null;

        // Load the object from the database
        comment = commentRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(comment);
        assertEquals(message, comment.getMessage());
       
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
        String message = "message test";

        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setTicket(ticket);

        
        // Save the object from the database
        commentRepository.save(comment);
        int id = comment.getId();
        comment = null;

        // Load the object from the database
        comment = commentRepository.findById(id);

        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(comment);
        assertEquals(message, comment.getMessage());
        assertEquals(ticketId, comment.getTicket().getId());
        assertEquals(title, comment.getTicket().getTitle());
        assertEquals(description, comment.getTicket().getDescription());
        assertEquals(priority, comment.getTicket().getPriority());
        assertEquals(status, comment.getTicket().getStatus());
        assertEquals(type, comment.getTicket().getType());
    }
    
    @Test
    @Transactional
    public void testAssignCommenter() {
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
        String message = "message test";

        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setCommenter(developer);

        
        // Save the object from the database
        commentRepository.save(comment);
        int id = comment.getId();
        comment = null;

        // Load the object from the database
        comment = commentRepository.findById(id);

        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(comment);
        assertEquals(message, comment.getMessage());
        assertEquals(username, comment.getCommenter().getUsername());
        assertEquals(password, comment.getCommenter().getPassword());
        assertEquals(email, comment.getCommenter().getEmail());
        assertEquals(firstName, comment.getCommenter().getFirstName());
        assertEquals(lastName, comment.getCommenter().getLastName());
    }
}
