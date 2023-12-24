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
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.TicketHistory;
import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTicketPersistence {
	@Autowired private TicketAttachmentRepository ticketAttachmentRepository;
	
	@Autowired private TicketRepository ticketRepository;
	@Autowired private TicketHistoryRepository ticketHistoryRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private DeveloperRepository developerRepository;
	@Autowired private ProjectRepository projectRepository;
	@Autowired private UserAccountRepository userAccountRepository;
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database
    	ticketRepository.deleteAll();
    	projectRepository.deleteAll();
    	developerRepository.deleteAll();
    	ticketAttachmentRepository.deleteAll();
    	commentRepository.deleteAll();;
    	userAccountRepository.deleteAll();
    	
        
    }
        
    @Test
    @Transactional
    public void testPersistAndLoadTicket() {
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
        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(id, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
       
    }
    
    @Test
    @Transactional
    public void testAssignTicketAttachment() {
        // Setting primitave attributes for customerAccount
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
		  ticket.setTicketAttachments(ticketAttachments);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();

        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(ticketId);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
       
       
        assertEquals(1, ticket.getTicketAttachments().size());
        for (TicketAttachment t : ticket.getTicketAttachments()) {
            if (t.getId() == id) {
                assertEquals(file, t.getFile());
                assertEquals(notes, t.getNotes());
                break;
            }
        }
    }
    
    @Test
    @Transactional
    public void testAssignTicketHistory() {
        // Setting primitave attributes for customerAccount
        String propertyChanged = "test property";
        String oldValueOfProperty = "old";
        String newValueOfProperty = "new";

        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setPropertyChanged(propertyChanged);
        ticketHistory.setOldValueOfProperty(oldValueOfProperty);
        ticketHistory.setNewValueOfProperty(newValueOfProperty);
        // Save the object from the database
        ticketHistoryRepository.save(ticketHistory);
        int historyId = ticketHistory.getId();
    	Set<TicketHistory> ticketHistories = new HashSet<TicketHistory>();
    	ticketHistories.add(ticketHistory);
    	
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
		  ticket.setTicketHistories(ticketHistories);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();

        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(ticketId);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
       
       
        assertEquals(1, ticket.getTicketHistories().size());
        for (TicketHistory t : ticket.getTicketHistories()) {
            if (t.getId() == historyId) {
                assertEquals(propertyChanged, t.getPropertyChanged());
                assertEquals(oldValueOfProperty, t.getOldValueOfProperty());
                assertEquals(newValueOfProperty, t.getNewValueOfProperty());
                break;
            }
        }
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
		  ticket.setComments(comments);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();

        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(ticketId);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
       
       
        assertEquals(1, ticket.getComments().size());
        for (Comment c : ticket.getComments()) {
            if (c.getId() == id) {
            	assertEquals(message, c.getMessage());
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
		  ticket.setAssignedDevelopers(developers);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();

        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(ticketId);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
        assertEquals(1, ticket.getAssignedDevelopers().size());
        for (Developer d : ticket.getAssignedDevelopers()) {
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
    public void testAssignSubmitter() {
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        UserAccount submitter = new ProjectManager();
        submitter.setUsername(username);
        submitter.setPassword(password);
        submitter.setEmail(email);
        submitter.setFirstName(firstName);
        submitter.setLastName(lastName);

        // Save the object from the database
        userAccountRepository.save(submitter);
    	int id = submitter.getId();
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
		  ticket.setSubmitter(submitter);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();

        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(ticketId);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
        assertEquals(id, ticket.getSubmitter().getId());
        assertEquals(username, ticket.getSubmitter().getUsername());
        assertEquals(password, ticket.getSubmitter().getPassword());
        assertEquals(email, ticket.getSubmitter().getEmail());
        assertEquals(firstName, ticket.getSubmitter().getFirstName());
        assertEquals(lastName, ticket.getSubmitter().getLastName());
    }
    
    @Test
    @Transactional
    public void testAssignProject() {
    	String name = "ProjectName";
    	String descriptionP = "testing";
    	
    	Project project = new Project();
    	project.setName(name);
    	project.setDescription(descriptionP);
    	
    	projectRepository.save(project);
    	int id = project.getId();
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
		  ticket.setProject(project);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();

        ticket = null;

        // Load the object from the database
        ticket = ticketRepository.findById(ticketId);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
        assertEquals(title, ticket.getTitle());
        assertEquals(description, ticket.getDescription());
        assertEquals(priority, ticket.getPriority());
        assertEquals(status, ticket.getStatus());
        assertEquals(type, ticket.getType());
        assertEquals(id, ticket.getProject().getId());
        assertEquals(name, ticket.getProject().getName());
        assertEquals(descriptionP, ticket.getProject().getDescription());
    }
}
