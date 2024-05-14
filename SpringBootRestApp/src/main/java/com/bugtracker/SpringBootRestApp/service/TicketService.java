package com.bugtracker.SpringBootRestApp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.dao.CommentRepository;
import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketHistoryRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketAttachmentRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.model.Comment;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.TicketHistory;
import com.bugtracker.SpringBootRestApp.model.UserAccount;

import org.springframework.stereotype.Service;


@Service
public class TicketService {

	@Autowired TicketRepository ticketRepository;
	@Autowired TicketAttachmentRepository ticketAttachmentRepository;
	@Autowired TicketHistoryRepository ticketHistoryRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired DeveloperRepository developerRepository;
	@Autowired ProjectRepository projectRepository;
	@Autowired UserAccountRepository userRepository;
	
	@Transactional
	public Ticket getTicketForTicketHistory(int id) {
		TicketHistory ticketHistory = ticketHistoryRepository.findById(id);
		return ticketHistory.getTicket();
	}

	
	@Transactional
	public UserAccount getSubmitterForTicket(int id) {
		Ticket ticket = ticketRepository.findById(id);
		return ticket.getSubmitter();
	}
	
	@Transactional
	public Ticket getTicketOfComment(int commentId) {
		Comment comment = commentRepository.findById(commentId);
		return comment.getTicket();
	}
	
	@Transactional
	public Ticket getTicketForTicketAttachment(int id) {
		TicketAttachment ticketAttachment = ticketAttachmentRepository.findById(id);
		return ticketAttachment.getTicket();
	}
	
	@Transactional
	public UserAccount getCommenterForComment(int commentId) {
		Comment comment = commentRepository.findById(commentId);
		return comment.getCommenter();
	}
	
	@Transactional
	public UserAccount getCreatorForTicketAttachment(int id) {
		TicketAttachment ticketAttachment = ticketAttachmentRepository.findById(id);
		return ticketAttachment.getCreator();
	}
	
	@Transactional
	public Ticket getTicket(int id) {
		return ticketRepository.findById(id);
	}
	
	@Transactional
	public List<Ticket> getAllTicketsWithPriority(Ticket.TicketPriority priority){
		return toList(ticketRepository.findByPriority(priority));
	}

	@Transactional
	public List<Ticket> getAllTicketsWithStatus(Ticket.TicketStatus status){
		return toList(ticketRepository.findByStatus(status));
	}
	
	@Transactional
	public List<Ticket> getAllTicketsWithType(Ticket.TicketType type){
		return toList(ticketRepository.findByType(type));
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForDeveloper(int developerId) {
		Developer developer = developerRepository.findById(developerId);
		return toList(developer.getAssignedTickets());
	}
	
	@Transactional
	public List<Developer> getAllDevelopersForTicket(int ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId);
		return toList(ticket.getAssignedDevelopers());
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForDeveloperWithUsername(String developerUsername) {
		return toList(ticketRepository.findByAssignedDevelopersUsername(developerUsername));
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForSubmitter(int submitterId) {
		UserAccount submitter = userRepository.findById(submitterId);
		return toList(submitter.getCreatedTickets());
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForSubmitterWithUsername(String submitterUsername) {
		return toList(ticketRepository.findBySubmitterUsername(submitterUsername));
	}
	
	
	@Transactional
	public List<Ticket> getAllTicketsForProject(int projectId){
		return ticketRepository.findByProjectId(projectId);
	}
	
	@Transactional
	public List<TicketAttachment> getAllTicketAttachmentForTicket(int ticketId){
		Ticket ticket = ticketRepository.findById(ticketId);
		return toList(ticket.getTicketAttachments());
	}
	
	@Transactional
	public List<TicketAttachment> getAllTicketAttachmentForUser(String username){
		UserAccount user = userRepository.findByUsername(username);
		return toList(user.getTicketAttachments());
	}
	
	
	@Transactional
	public List<TicketHistory> getAllTicketHistoryForTicket(int ticketId){
		Ticket ticket = ticketRepository.findById(ticketId);
		return toList(ticket.getTicketHistories());
	}
	
	@Transactional
	public List<Comment> getAllCommentForTicket(int ticketId){
		Ticket ticket = ticketRepository.findById(ticketId);
		List<Comment> comments = toList(ticket.getComments());
		
		// Sort comments by creation date
		Collections.sort(comments, new Comparator<Comment>() {
			  public int compare(Comment c1, Comment c2) {
			      return c2.getCreationDate().compareTo(c1.getCreationDate());
			  }
			});
		return comments;
	}
	
	@Transactional
	public List<Comment> getAllCommentForUser(String username){
		UserAccount user = userRepository.findByUsername(username);
		return toList(user.getComments());
	}
	
	@Transactional 
	public Ticket createTicket(
			String title,
			String description,
			Ticket.TicketPriority priority,
			Ticket.TicketStatus status,
			Ticket.TicketType type,
			Set<TicketAttachment> ticketAttachments,
			Set<Developer> developers,
			Set<Comment> comments,
			Integer projectId,
			String submitterUsername) {
		String error = "";
		if(title == null) {
			error += "Title cannot be empty";
		}
		if(priority == null) {
			error += "Priority cannot be empty";
		}
		if(status == null) {
			error += "Status cannot be empty";
		}
		if(type == null) {
			error += "Type cannot be empty";
		}
		if(projectId == null) {
			error += "Project cannot be empty";
		}
		if(submitterUsername == null) {
			error += "Submitter cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		
		Ticket ticket = new Ticket();
		
		ticket.setTitle(title);
		ticket.setDescription(description);
		ticket.setPriority(priority);
		ticket.setType(type);
		ticket.setStatus(status);
		ticket.setTicketAttachments(ticketAttachments);
		ticket.setAssignedDevelopers(developers);
		ticket.setComments(comments);
		Project project = projectRepository.findById(projectId);
		ticket.setProject(project);
		System.out.println("CHECK PROJECT ");
		System.out.println(project.getProjectManager().getFirstName());
		UserAccount submitter = userRepository.findByUsername(submitterUsername);
		ticket.setSubmitter(submitter);
		
		ticketRepository.save(ticket);
		return ticket;
	}
	
	@Transactional
	public Ticket setProject(
			Integer id,
			Integer projectId) {
        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket does not exist!");
        }
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        Ticket ticket = ticketRepository.findById(id);
        Project project = projectRepository.findById(projectId);
        
        if(!(ticket.getProject().getId() == projectId)) {
        	
        	addTicketHistory(id, "project", ticket.getProject().getName(), project.getName());
        	ticket.setProject(project);
        }
        ticketRepository.save(ticket);
        return ticket;
	}
	
	@Transactional 
	public Ticket addTicketAttachment(
			Integer ticketId,
			String file,
			String notes,
			String creatorUsername) {
		String error = "";
		if(file == null) {
			error += "File cannot be empty";
		}
		if(ticketId == null) {
			error += "Ticket ID cannot be empty";
		}
		if(creatorUsername == null) {
			error += "Ticket attachment creator username cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		
		TicketAttachment ticketAttachment = new TicketAttachment();
		ticketAttachment.setFile(file);
		ticketAttachment.setNotes(notes);
		Ticket ticket = ticketRepository.findById(ticketId);
		ticketAttachment.setTicket(ticket);
		UserAccount creator = userRepository.findByUsername(creatorUsername);
		ticketAttachment.setCreator(creator);
		ticketAttachmentRepository.save(ticketAttachment);
		
        if(ticket.getTicketAttachments()== null) {
        	Set<TicketAttachment> ticketAttachments = new HashSet<TicketAttachment>();
        	ticketAttachments.add(ticketAttachment);
        	ticket.setTicketAttachments(ticketAttachments);
        	
        }else {
        	ticket.getTicketAttachments().add(ticketAttachment);
        }
		ticketRepository.save(ticket);
		return ticket;
	}
	
	@Transactional
	public Ticket addTicketHistory(
			Integer ticketId,
			String propertyChanged,
			String oldValueOfProperty,
			String newValueOfProperty) {
		
		String error = "";
		if(propertyChanged == null) {
			error += "Property cannot be empty";
		}
		if(oldValueOfProperty == null) {
			error += "Old property value cannot be empty";
		}
		if(newValueOfProperty == null) {
			error += "New property value cannot be empty";
		}
		if(ticketId == null) {
			error += "Ticket ID cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		TicketHistory ticketHistory = new TicketHistory();
		ticketHistory.setPropertyChanged(propertyChanged);
		ticketHistory.setOldValueOfProperty(oldValueOfProperty);
		ticketHistory.setNewValueOfProperty(newValueOfProperty);
		Ticket ticket = ticketRepository.findById(ticketId);
		ticketHistory.setTicket(ticket);
		ticketHistoryRepository.save(ticketHistory);
        if(ticket.getTicketHistories()== null) {
        	Set<TicketHistory> ticketHistories = new HashSet<TicketHistory>();
        	ticketHistories.add(ticketHistory);
        	ticket.setTicketHistories(ticketHistories);
        	
        }else {
        	ticket.getTicketHistories().add(ticketHistory);
        }
        ticketRepository.save(ticket);
		return ticket;
	}

	@Transactional
	public Ticket createComment(
			String message,
			Integer ticketId,
			String commenterUsername) {
		String error = "";
		if(message == null) {
			error += "Message cannot be empty";
		}
		if(commenterUsername == null) {
			error += "Commenter username cannot be empty";
		}
		if(ticketId == null) {
			error += "Ticket ID cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		Comment comment = new Comment();
		comment.setMessage(message);
		Ticket ticket = ticketRepository.findById(ticketId);
		comment.setTicket(ticket);
		UserAccount commenter = userRepository.findByUsername(commenterUsername);
		comment.setCommenter(commenter);	
		commentRepository.save(comment);
        if(ticket.getComments()== null) {
        	Set<Comment> comments = new HashSet<Comment>();
        	comments.add(comment);
        	ticket.setComments(comments);
        	
        }else {
        	ticket.getComments().add(comment);
        }
        ticketRepository.save(ticket);
		return ticket;
	}
	
    @Transactional
    public Ticket deleteTicket(int id) {
        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket does not exist!");
        }
        
        Ticket ticket = ticketRepository.findById(id);
        ticketRepository.delete(ticket);
        return ticket;
    }
    
    @Transactional
    public TicketAttachment deleteTicketAttachment(int id) {
        if (!ticketAttachmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket attachment does not exist!");
        }
        
        TicketAttachment ticketAttachment = ticketAttachmentRepository.findById(id);
        ticketAttachmentRepository.delete(ticketAttachment);
        return ticketAttachment;
    }
    
    @Transactional
    public TicketHistory deleteTicketHistory(int id) {
        if (!ticketHistoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket history does not exist!");
        }
        
        TicketHistory ticketHistory = ticketHistoryRepository.findById(id);
        ticketHistoryRepository.delete(ticketHistory);
        return ticketHistory;
    }
    
    @Transactional
    public Comment deleteComment(int id) {
        if (!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("Comment does not exist!");
        }
        
        Comment comment = commentRepository.findById(id);
        commentRepository.delete(comment);
        return comment;
    }
    
    @Transactional
    public Ticket addAssignedDeveloper(
    		Integer ticketId,
    		String developerUsername) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new IllegalArgumentException("Ticket does not exist!");
        }
        if (!developerRepository.existsByUsername(developerUsername)) {
            throw new IllegalArgumentException("Developer does not exist!");
        }
        
        Ticket ticket = ticketRepository.findById(ticketId);
        Developer developer = developerRepository.findByUsername(developerUsername);
        if(ticket.getAssignedDevelopers() == null) {
        	Set<Developer> developers = new HashSet<Developer>();
        	developers.add(developer);
        	ticket.setAssignedDevelopers(developers);
        	
        }else {
        	ticket.getAssignedDevelopers().add(developer);
        }
        ticketRepository.save(ticket);
        return ticket;
    }
    
    @Transactional
    public Ticket modifyTicket(
    		Integer id,
			String title,
			String description,
			Ticket.TicketPriority priority,
			Ticket.TicketStatus status,
			Ticket.TicketType type) {
        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket does not exist!");
        }
        Ticket ticket = ticketRepository.findById(id);
        if(!ticket.getTitle().equals(title)) {
        	addTicketHistory(id, "title", ticket.getTitle(),
        			title);
        	ticket.setTitle(title);
        }
        
        if(!ticket.getDescription().equals(description)) {
        	addTicketHistory(id, "description", ticket.getDescription(),
        			description);
        	ticket.setDescription(description);
        }
        if(!ticket.getPriority().equals(priority)) {
        	addTicketHistory(id, "priority", ticket.getPriority().toString(),
        			priority.toString());
        	ticket.setPriority(priority);
        }
        if(!ticket.getStatus().equals(status)) {
        	addTicketHistory(id, "status", ticket.getStatus().toString(),
        			status.toString());
        	ticket.setStatus(status);
        }   
        if(!ticket.getType().equals(type)) {
        	addTicketHistory(id, "type", ticket.getType().toString(),
        			type.toString());
        	ticket.setType(type);
        } 
        ticketRepository.save(ticket);
        
        return ticket;
    	
    }
    
	@Transactional
	public Ticket setAssignedDevelopers(
			Integer ticketId,
			List<String> usernames) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new IllegalArgumentException("Ticket does not exist!");
        }
        Ticket ticket = ticketRepository.findById(ticketId);
        Set<Developer> developers = new HashSet<Developer>();
        for (String username: usernames) {
	        if (!developerRepository.existsByUsername(username)) {
	            throw new IllegalArgumentException("Developer does not exist!");
	        }
	        Developer developer = developerRepository.findByUsername(username);
	        developers.add(developer);
        }
        ticket.setAssignedDevelopers(developers);
        ticketRepository.save(ticket);
        return ticket;
	}
    
	@Transactional
	public Comment modifyComment(
			String message,
			Integer commentId) {
		String error = "";
		if(commentId == null) {
			error += "Comment ID cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment does not exist!");
        }
        Comment comment = commentRepository.findById(commentId);
        
		if(message != null) {
			comment.setMessage(message);
			commentRepository.save(comment);
		}
		
		return comment;
	}
	
	
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        if (iterable != null) {
            for (T t : iterable) {
                resultList.add(t);
            }
        }
        return resultList;
    }
	
	

}
