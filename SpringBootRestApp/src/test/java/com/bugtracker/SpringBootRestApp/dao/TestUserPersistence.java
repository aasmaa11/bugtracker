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
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;
import com.bugtracker.SpringBootRestApp.model.UserAccount;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserPersistence {
	@Autowired private UserAccountRepository userAccountRepository;
	
	@Autowired private CommentRepository commentRepository;
	
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	userAccountRepository.deleteAll();
    	commentRepository.deleteAll();
        
    }
        
     
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
    }
}
