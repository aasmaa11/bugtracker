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
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCommentPersistence {
	
	@Autowired private CommentRepository commentRepository;
	
	@Autowired private TicketRepository ticketRepository;
	
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	ticketRepository.deleteAll();
    	commentRepository.deleteAll();
    	
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
        System.out.println(comment);
        
        // Save the object from the database
        commentRepository.save(comment);
        int id = comment.getId();
        comment = null;

        // Load the object from the database
        comment = commentRepository.findById(id);
        System.out.println(id);
        System.out.println(comment);
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
}
