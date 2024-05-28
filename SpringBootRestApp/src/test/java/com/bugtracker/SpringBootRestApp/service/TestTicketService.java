package com.bugtracker.SpringBootRestApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.bugtracker.SpringBootRestApp.dao.CommentRepository;
import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketAttachmentRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketHistoryRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Comment;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.TicketHistory;
import com.bugtracker.SpringBootRestApp.model.UserAccount;

@ExtendWith(MockitoExtension.class)
public class TestTicketService {
	
	@Mock private TicketRepository ticketRepository;
	@Mock private TicketAttachmentRepository ticketAttachmentRepository;
	@Mock private TicketHistoryRepository ticketHistoryRepository;
	@Mock private CommentRepository commentRepository;
	@Mock private DeveloperRepository developerRepository;
	@Mock private ProjectRepository projectRepository;
	@Mock private UserAccountRepository userRepository;
	
    @InjectMocks private TicketService ticketService;
    
    private static final int TICKET_KEY = 12;
    private static final String TICKET_TITLE = "test title";
    private static final String TICKET_DESCRIPTION = "test description";
    private static final int TICKET_ATTACHMENT_KEY = 102;
    private static final int TICKET_HISTORY_KEY = 110;
    private static final int COMMENT_KEY = 15;
    private static final Ticket.TicketStatus TICKET_STATUS = Ticket.TicketStatus.InProgress;
    private static final Ticket.TicketPriority TICKET_PRIORITY = Ticket.TicketPriority.High;
    private static final Ticket.TicketType TICKET_TYPE = Ticket.TicketType.Bug;
    private static final Long DEVELOPER_KEY = (long) 20;
    private static final String DEVELOPER_USERNAME = "testDeveloper";
    private static final String DEV_2_KEY = "testDeveloper2";
    private static final int PROJECT_KEY = 19;
    
    
    
    @BeforeEach
    public void setMockOutput() {
        lenient()
        .when(developerRepository.findByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_USERNAME)) {
                        Developer developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        return developer;
                    } else {
                        return null;
                    }
                });
        
        lenient()
        .when(developerRepository.findById(anyLong()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_KEY)) {
                        Developer developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        ticket.setTitle(TICKET_TITLE);
                        ticket.setDescription(TICKET_DESCRIPTION);
                        Set<Ticket> tickets = new HashSet<Ticket>();
                        tickets.add(ticket);
                        developer.setAssignedTickets(tickets);
                        return developer;
                    } else {
                        return null;
                    }
                });
        
        lenient()
        .when(userRepository.findById(anyLong()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_KEY)) {
                        Developer developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        ticket.setTitle(TICKET_TITLE);
                        ticket.setDescription(TICKET_DESCRIPTION);
                        Set<Ticket> tickets = new HashSet<Ticket>();
                        tickets.add(ticket);
                        TicketAttachment ticketAttachment = new TicketAttachment();
                        Set<TicketAttachment> ticketAttachments = new HashSet<TicketAttachment>();
                        ticketAttachments.add(ticketAttachment);                       
                        developer.setAssignedTickets(tickets);
                        developer.setTicketAttachments(ticketAttachments);
                        developer.setCreatedTickets(tickets);
                        return developer;
                    } else {
                        return null;
                    }
                });
        
        lenient()
        .when(commentRepository.findByCommenterUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEV_2_KEY)) {
                    	List<Comment> comments = new ArrayList<Comment>();
                    	
                    	Comment comment = new Comment();
                    	comment.setMessage("hello");
                    	comments.add(comment);
                        return comments;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                        Developer developer = new Developer();
                        developer.setUsername(DEV_2_KEY);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        Set<Developer> devs = new HashSet<Developer>();
                        devs.add(developer);
                        TicketHistory ticketHistory = new TicketHistory();
                        Set<TicketHistory> ticketHistories = new HashSet<TicketHistory>();
                        ticketHistories.add(ticketHistory);
                        TicketAttachment ticketAttachment = new TicketAttachment();
                        Set<TicketAttachment> ticketAttachments = new HashSet<TicketAttachment>();
                        ticketAttachments.add(ticketAttachment);   
                        Comment comment = new Comment();
                        Set<Comment> comments = new HashSet<Comment>();
                        comments.add(comment); 
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        ticket.setTitle(TICKET_TITLE);
                        ticket.setDescription(TICKET_DESCRIPTION);
                        ticket.setAssignedDevelopers(devs);
                        ticket.setTicketHistories(ticketHistories);
                        ticket.setTicketAttachments(ticketAttachments);
                        ticket.setSubmitter(developer);
                        ticket.setComments(comments);
                        return ticket;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByCommentsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(COMMENT_KEY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        ticket.setTitle(TICKET_TITLE);
                        ticket.setDescription(TICKET_DESCRIPTION);
                        return ticket;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByTicketAttachmentsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_ATTACHMENT_KEY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        ticket.setTitle(TICKET_TITLE);
                        ticket.setDescription(TICKET_DESCRIPTION);
                        return ticket;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByTicketHistoriesId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_HISTORY_KEY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        ticket.setTitle(TICKET_TITLE);
                        ticket.setDescription(TICKET_DESCRIPTION);
                        return ticket;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByPriority(any(Ticket.TicketPriority.class)))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_PRIORITY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByStatus(any(Ticket.TicketStatus.class)))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_STATUS)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByType(any(Ticket.TicketType.class)))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_TYPE)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	
    	lenient()
        .when(ticketRepository.findByAssignedDevelopersId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_KEY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findBySubmitterId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_KEY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findByAssignedDevelopersUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_USERNAME)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketRepository.findBySubmitterUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_USERNAME)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	
    	lenient()
        .when(ticketRepository.findByProjectId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PROJECT_KEY)) {
                    	Ticket ticket = new Ticket();
                        ticket.setPriority(TICKET_PRIORITY);
                        ticket.setStatus(TICKET_STATUS);
                        ticket.setType(TICKET_TYPE);
                        List<Ticket> tickets = new ArrayList<Ticket>();
                        tickets.add(ticket);
                        return tickets;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketAttachmentRepository.findByTicketId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                    	List<TicketAttachment> ticketAttachments = new ArrayList<TicketAttachment>();
                    	
                    	TicketAttachment ticketAttachment = new TicketAttachment();
                    	ticketAttachment.setNotes("test notes");
                    	ticketAttachment.setFile("file 1");
                    	ticketAttachments.add(ticketAttachment);
                        return ticketAttachments;
                    } else {
                        return null;
                    }
                });
    	
    	
    	lenient()
        .when(ticketAttachmentRepository.findByCreatorUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEV_2_KEY)) {
                    	List<TicketAttachment> ticketAttachments = new ArrayList<TicketAttachment>();
                    	
                    	TicketAttachment ticketAttachment = new TicketAttachment();
                    	ticketAttachment.setNotes("test notes");
                    	ticketAttachment.setFile("file 1");
                    	ticketAttachments.add(ticketAttachment);
                        return ticketAttachments;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketAttachmentRepository.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_ATTACHMENT_KEY)) {
                    	
                    	TicketAttachment ticketAttachment = new TicketAttachment();
                    	ticketAttachment.setNotes("test notes");
                    	ticketAttachment.setFile("file 1");
                        return ticketAttachment;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketHistoryRepository.findByTicketId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                    	List<TicketHistory> ticketHistories = new ArrayList<TicketHistory>();
                    	
                    	TicketHistory ticketHistory = new TicketHistory();
                    	ticketHistory.setPropertyChanged("priority");
                    	ticketHistory.setOldValueOfProperty("high");
                    	ticketHistory.setNewValueOfProperty("low");
                    	ticketHistories.add(ticketHistory);
                        return ticketHistories;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(ticketHistoryRepository.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_HISTORY_KEY)) {
                    	Ticket ticket = new Ticket();
                    	ticket.setPriority(TICKET_PRIORITY);
                    	ticket.setStatus(TICKET_STATUS);
                    	ticket.setType(TICKET_TYPE);
                    	TicketHistory ticketHistory = new TicketHistory();
                    	ticketHistory.setPropertyChanged("priority");
                    	ticketHistory.setOldValueOfProperty("high");
                    	ticketHistory.setNewValueOfProperty("low");
                    	ticketHistory.setTicket(ticket);
                        return ticketHistory;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(commentRepository.findByTicketId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                    	List<Comment> comments = new ArrayList<Comment>();
                    	
                    	Comment comment = new Comment();
                    	comment.setMessage("hello");
                    	comments.add(comment);
                        return comments;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(developerRepository.findByAssignedTicketsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                    	List<Developer> developers = new ArrayList<Developer>();
                    	
                    	Developer developer = new Developer();
                    	developer.setUsername(DEV_2_KEY);
                    	developers.add(developer);
                        return developers;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(commentRepository.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(COMMENT_KEY)) {
                    	Ticket ticket = new Ticket();
                    	ticket.setPriority(TICKET_PRIORITY);
                    	ticket.setStatus(TICKET_STATUS);
                    	ticket.setType(TICKET_TYPE);
                    	Developer developer = new Developer();
                    	developer.setUsername(DEVELOPER_USERNAME);
                    	Comment comment = new Comment();
                    	comment.setMessage("hello");
                    	comment.setCommenter(developer);
                    	comment.setTicket(ticket);
                        return comment;
                    } else {
                        return null;
                    }
                });
    	
        lenient()
        .when(projectRepository.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PROJECT_KEY)) {
                        Project project = new Project();
                        project.setName("project 1");
                        project.setDescription("description 1");
                        return project;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(userRepository.findByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_USERNAME)) {
                    	UserAccount developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        return developer;
                    } else if (invocation.getArgument(0).equals(DEV_2_KEY)) {
                    	UserAccount developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        Comment comment = new Comment();
                        Set<Comment> comments = new HashSet<Comment>();
                        comments.add(comment);
                        TicketAttachment ticketAttachment = new TicketAttachment();
                        Set<TicketAttachment> ticketAttachments = new HashSet<TicketAttachment>();
                        ticketAttachments.add(ticketAttachment);
                        developer.setComments(comments);
                        developer.setTicketAttachments(ticketAttachments);
                        return developer;
                    } else {
                        return null;
                    }
                });
    	
    	
    	lenient()
        .when(userRepository.findByCreatedTicketsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                    	UserAccount developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        return developer;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(userRepository.findByCommentsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(COMMENT_KEY)) {
                    	UserAccount developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        return developer;
                    } else {
                        return null;
                    }
                });
    	
    	lenient()
        .when(userRepository.findByTicketAttachmentsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_ATTACHMENT_KEY)) {
                    	UserAccount developer = new Developer();
                        developer.setUsername(DEVELOPER_USERNAME);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        return developer;
                    } else {
                        return null;
                    }
                });
    	
        lenient()
        .when(developerRepository.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(DEVELOPER_USERNAME);
                });
        
        
        lenient()
        .when(ticketRepository.existsById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(TICKET_KEY);
                });
        
        lenient()
        .when(ticketHistoryRepository.existsById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(TICKET_HISTORY_KEY);
                });
        
        
        lenient()
        .when(ticketAttachmentRepository.existsById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(TICKET_ATTACHMENT_KEY);
                });
        
        lenient()
        .when(commentRepository.existsById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(COMMENT_KEY);
                });
        
        
        lenient()
        .when(userRepository.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(DEVELOPER_KEY);
                });
    	
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };
        lenient()
                .when(userRepository.save(any(UserAccount.class)))
                .thenAnswer(returnParameterAsAnswer);
        
        lenient()
        .when(developerRepository.save(any(Developer.class)))
        .thenAnswer(returnParameterAsAnswer);
        
        lenient()
                .when(projectRepository.save(any(Project.class)))
                .thenAnswer(returnParameterAsAnswer);
        lenient()
        .when(commentRepository.save(any(Comment.class)))
        .thenAnswer(returnParameterAsAnswer);
        lenient()
        .when(ticketHistoryRepository.save(any(TicketHistory.class)))
        .thenAnswer(returnParameterAsAnswer);
        
        lenient()
        .when(ticketAttachmentRepository.save(any(TicketAttachment.class)))
        .thenAnswer(returnParameterAsAnswer);
        
        lenient()
        .when(ticketRepository.save(any(Ticket.class)))
        .thenAnswer(returnParameterAsAnswer);
        
        
    }
    
    @Test
    public void testGetSubmitterForTicket() {
    	UserAccount submitter = ticketService.getSubmitterForTicket(TICKET_KEY);
    	assertNotNull(submitter);
    	assertEquals(DEV_2_KEY, submitter.getUsername());
    }
    
    @Test
    public void testGetAllDevelopersForTicket() {
        List<Developer> developers = ticketService.getAllDevelopersForTicket(TICKET_KEY);
        assertEquals(1, developers.size());
        Developer developer = developers.get(0);
        assertEquals(DEV_2_KEY, developer.getUsername());
    }
    
    @Test
    public void testGetTicketOfComment() {
    	Ticket ticket = ticketService.getTicketOfComment(COMMENT_KEY);

    	assertNotNull(ticket);
    	assertEquals(TICKET_PRIORITY, ticket.getPriority());
    	assertEquals(TICKET_STATUS, ticket.getStatus());
    	assertEquals(TICKET_TYPE, ticket.getType()); 
    }
    
    @Test
    public void testGetTicketForTicketAttachment() {
    	Ticket ticket = ticketService.getTicketForTicketAttachment(TICKET_ATTACHMENT_KEY);

    	assertNotNull(ticket);
    	assertEquals(TICKET_PRIORITY, ticket.getPriority());
    	assertEquals(TICKET_STATUS, ticket.getStatus());
    	assertEquals(TICKET_TYPE, ticket.getType()); 
    }
    
    @Test
    public void testGetTicketForTicketHistory() {
    	Ticket ticket = ticketService.getTicketForTicketHistory(TICKET_HISTORY_KEY);

    	assertNotNull(ticket);
    	assertEquals(TICKET_PRIORITY, ticket.getPriority());
    	assertEquals(TICKET_STATUS, ticket.getStatus());
    	assertEquals(TICKET_TYPE, ticket.getType()); 
    }
    
    @Test
    public void testGetCommenterForComment() {
    	UserAccount user = ticketService.getCommenterForComment(COMMENT_KEY);

    	assertNotNull(user);
    	assertEquals(DEVELOPER_USERNAME, user.getUsername());
    }
    
    @Test
    public void testGetCreatorForTicketAttachment() {
    	UserAccount user = ticketService.getCreatorForTicketAttachment(TICKET_ATTACHMENT_KEY);

    	assertNotNull(user);
    	assertEquals(DEVELOPER_USERNAME, user.getUsername());
    }
    
    @Test
    public void testGetTicket() {
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.getTicket(TICKET_KEY);
    	} catch (IllegalArgumentException e) {
            fail();
        }
    	assertNotNull(ticket);
    	assertEquals(TICKET_PRIORITY, ticket.getPriority());
    	assertEquals(TICKET_STATUS, ticket.getStatus());
    	assertEquals(TICKET_TYPE, ticket.getType()); 	
    }
    
    @Test
    public void testGetAllTicketsWithPriority() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsWithPriority(TICKET_PRIORITY);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketsWithStatus() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsWithStatus(TICKET_STATUS);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketsWithType() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsWithType(TICKET_TYPE);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketsForDeveloper() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsForDeveloper(DEVELOPER_KEY);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketsForDeveloperWithUsername() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsForDeveloperWithUsername(DEVELOPER_USERNAME);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketsForSubmitter() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsForSubmitter(DEVELOPER_KEY);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    
    @Test
    public void testGetAllTicketsForSubmitterWithUsername() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsForSubmitterWithUsername(DEVELOPER_USERNAME);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketsForProject() {
    	List<Ticket> tickets = null;
    	tickets = ticketService.getAllTicketsForProject(PROJECT_KEY);
    	
    	assertNotNull(tickets);
    	assertEquals(TICKET_PRIORITY, tickets.get(0).getPriority());
    	assertEquals(TICKET_STATUS, tickets.get(0).getStatus());
    	assertEquals(TICKET_TYPE, tickets.get(0).getType()); 
    }
    
    @Test
    public void testGetAllTicketAttachmentForTicket() {
    	List<TicketAttachment> ticketAttachments = null;
    	ticketAttachments = ticketService.getAllTicketAttachmentForTicket(TICKET_KEY);
    	
    	assertNotNull(ticketAttachments);
    	assertEquals(1, ticketAttachments.size());
    }
    
    @Test
    public void testGetAllTicketAttachmentForUser() {
    	List<TicketAttachment> ticketAttachments = null;
    	ticketAttachments = ticketService.getAllTicketAttachmentForUser(DEV_2_KEY);
    	
    	assertNotNull(ticketAttachments);
    	assertEquals(1, ticketAttachments.size());
    }
    
    
    @Test
    public void testGetAllTicketHistoryForTicket() {
    	List<TicketHistory> ticketHistories = null;
    	ticketHistories = ticketService.getAllTicketHistoryForTicket(TICKET_KEY);
    	
    	assertNotNull(ticketHistories);
    	assertEquals(1, ticketHistories.size());
    }
    
    @Test
    public void testGetAllCommentForTicket() {
    	List<Comment> comments = null;
    	comments = ticketService.getAllCommentForTicket(TICKET_KEY);
    	
    	assertNotNull(comments);
    	assertEquals(1, comments.size());
    }
    
    @Test
    public void testGetAllCommentForUser() {
    	List<Comment> comments = null;
    	comments = ticketService.getAllCommentForUser(DEV_2_KEY);
    	
    	assertNotNull(comments);
    	assertEquals(1, comments.size());
    }
    
    @Test
    public void testCreateTicket() {
    	String title = "title 1";
    	String description = "description 1";
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.createTicket(title, description, TICKET_PRIORITY, TICKET_STATUS, TICKET_TYPE, null, null, null, PROJECT_KEY, DEVELOPER_USERNAME);    		
    	} catch (IllegalArgumentException e) {
    		
            fail();
        }
    	
    	assertNotNull(ticket);
    	assertEquals(TICKET_PRIORITY, ticket.getPriority());
    	assertEquals(TICKET_STATUS, ticket.getStatus());
    	assertEquals(TICKET_TYPE, ticket.getType());
    	assertEquals(title, ticket.getTitle());
    	assertEquals(description, ticket.getDescription());
    	assertNotNull(ticket.getSubmitter());
    	assertEquals(DEVELOPER_USERNAME, ticket.getSubmitter().getUsername());
    	assertNotNull(ticket.getProject());
    	assertEquals("project 1", ticket.getProject().getName());
    	assertEquals("description 1", ticket.getProject().getDescription());
   }
    
    @Test
    public void testAddTicketAttachment() {
    	String notes = "hello";
    	String file = "file name";
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.addTicketAttachment(TICKET_KEY, file, notes, DEVELOPER_USERNAME); 		
    	} catch (IllegalArgumentException e) {
            fail();
        }
    	
        assertNotNull(ticket);
        for (TicketAttachment t : ticket.getTicketAttachments()) {
            if (t.getCreator() != null && t.getCreator().getUsername().equals(DEVELOPER_USERNAME)) {
                assertEquals(notes, t.getNotes());
                assertEquals(file, t.getFile());
                break;
            }
        }
    }
    
    @Test
    public void testAddTicketHistory() {
    	String propertyChanged = "priority";
    	String oldValue = "low";
    	String newValue = "high";
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.addTicketHistory(TICKET_KEY, propertyChanged, oldValue, newValue);
    	} catch (IllegalArgumentException e) {
            fail();
        }
    	
        assertNotNull(ticket);
        for (TicketHistory t : ticket.getTicketHistories()) {
        	if(t.getTicket()!= null && t.getTicket().getId() == TICKET_KEY) {
                assertEquals(propertyChanged, t.getPropertyChanged());
                assertEquals(oldValue, t.getOldValueOfProperty());
                assertEquals(newValue, t.getNewValueOfProperty());
                break;
        	}

        }
    }
    
    @Test
    public void testCreateComment() {
    	String message = "priority message";
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.createComment(message, TICKET_KEY, DEVELOPER_USERNAME);
    	} catch (IllegalArgumentException e) {
            fail();
        }
    	
        assertNotNull(ticket);
        for (Comment c : ticket.getComments()) {
        	if(c.getTicket() != null) {
                if (c.getTicket().getId() == TICKET_KEY) {
                    assertEquals(message, c.getMessage());
                    assertEquals(DEVELOPER_USERNAME, c.getCommenter().getUsername());
                    
                    break;
                }
        	}

        }
    }
    
    @Test
    public void testDeleteTicket() {
    	Ticket ticket = null;
        try {
            ticket = ticketService.deleteTicket(TICKET_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(ticket);
    }
    
    @Test
    public void testDeleteTicketAttachment() {
    	TicketAttachment ticketAttachment = null;
        try {
            ticketAttachment = ticketService.deleteTicketAttachment(TICKET_ATTACHMENT_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(ticketAttachment);
    }
    
    @Test
    public void testDeleteTicketHistory() {
    	TicketHistory ticketHistory = null;
        try {
        	ticketHistory = ticketService.deleteTicketHistory(TICKET_HISTORY_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(ticketHistory);
    }
    
    @Test
    public void testDeleteComment() {
    	Comment comment = null;
        try {
        	comment = ticketService.deleteComment(COMMENT_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(comment);
    }
    
    @Test
    public void testAddAssignedDeveloper() {
        String firstName = "Joe";
        String lastName = "Jones";
        String email = "pm@email.com";
        String password = "1111";
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.addAssignedDeveloper(TICKET_KEY, DEVELOPER_USERNAME);		
    	} catch (IllegalArgumentException e) {
    		System.out.println(e.getMessage());
            fail();
        }
    	
        assertNotNull(ticket);
        for (Developer d : ticket.getAssignedDevelopers()) {
            if (d.getUsername().equals(DEVELOPER_USERNAME)) {
                assertEquals(password, d.getPassword());
                assertEquals(email, d.getEmail());
                assertEquals(firstName, d.getFirstName());
                assertEquals(lastName, d.getLastName());
                break;
            }
        }
    }
    
    @Test
    public void testModifyTicket() {
    	Ticket.TicketPriority newPriority = Ticket.TicketPriority.None;
    	Ticket.TicketStatus newStatus = Ticket.TicketStatus.Resolved;
    	Ticket.TicketType newType = Ticket.TicketType.DocRequest;
    	String title = "new title";
    	String description = "new description";
    	Ticket ticket = null;
    	try {
    		ticket = ticketService.modifyTicket(TICKET_KEY, title, description, newPriority, newStatus, newType);	
    	} catch (IllegalArgumentException e) {
            fail();
        }
    	
    	assertNotNull(ticket);
    	assertEquals(newPriority, ticket.getPriority());
    	assertEquals(newStatus, ticket.getStatus());
    	assertEquals(newType, ticket.getType()); 
    	assertEquals(title, ticket.getTitle());
    	assertEquals(description, ticket.getDescription());
    }
    
    @Test
    public void testModifyComment() {
    	String message = "new message";
		Comment comment = null;
    	try {
    		comment = ticketService.modifyComment(message, COMMENT_KEY);
    	} catch (IllegalArgumentException e) {
            fail();
        }
    	assertNotNull(comment);
    	assertEquals(message, comment.getMessage());
    	
    }
}
