package com.bugtracker.SpringBootRestApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.dao.CommentRepository;
import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.SubmitterRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketHistoryRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketAttachmentRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserRepository;
import com.bugtracker.SpringBootRestApp.model.Comment;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.Submitter;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.TicketHistory;
import com.bugtracker.SpringBootRestApp.model.User;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@Service
public class TicketService {
	
	@Autowired TicketRepository ticketRepository;
	@Autowired TicketAttachmentRepository ticketAttachmentRepository;
	@Autowired TicketHistoryRepository ticketHistoryRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired DeveloperRepository developerRepository;
	@Autowired ProjectRepository projectRepository;
	@Autowired SubmitterRepository submitterRepository;
	@Autowired UserRepository userRepository;
	
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
		return toList(ticketRepository.findByDeveloperId(developerId));
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForSubmitter(int submitterId) {
		return toList(ticketRepository.findBySubmitterId(submitterId));
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForDeveloper(String developerUsername) {
		return toList(ticketRepository.findByDeveloperUsername(developerUsername));
	}
	
	@Transactional
	public List<Ticket> getAllTicketsForProject(int projectId){
		return toList(ticketRepository.findByProjectId(projectId));
	}
	
	@Transactional
	public List<TicketAttachment> getAllTicketAttachmentForTicket(int ticketId){
		return toList(ticketAttachmentRepository.findByTicketId(ticketId));
	}
	
	@Transactional
	public List<TicketHistory> getAllTicketHistoryForTicket(int ticketId){
		return toList(ticketHistoryRepository.findByTicketId(ticketId));
	}
	
	@Transactional
	public List<Comment> getAllCommentForTicket(int ticketId){
		return toList(commentRepository.findByTicketId(ticketId));
	}
	
	@Transactional 
	public Ticket createTicket(
			String title,
			String description,
			Ticket.TicketPriority priority,
			Ticket.TicketStatus status,
			Ticket.TicketType type,
			Set<TicketAttachment> ticketAttachments,
			String developerUsername,
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
		Developer developer = developerRepository.findByUsername(developerUsername);
		ticket.setDeveloper(developer);
		ticket.setComments(comments);
		Project project = projectRepository.findById(projectId);
		ticket.setProject(project);
		Submitter submitter = submitterRepository.findByUsername(submitterUsername);
		ticket.setSubmitter(submitter);
		
		ticketRepository.save(ticket);
		return ticket;
	}
	
	@Transactional 
	public TicketAttachment createTicketAttachment(
			String file,
			String notes,
			Integer ticketId) {
		String error = "";
		if(file == null) {
			error += "File cannot be empty";
		}
		if(ticketId == null) {
			error += "Ticket ID cannot be empty";
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
		ticketAttachmentRepository.save(ticketAttachment);
		return ticketAttachment;
	}
	
	@Transactional
	public TicketHistory createTicketHistory(
			String propertyChanged,
			String oldValueOfProperty,
			String newValueOfProperty,
			Integer ticketId) {
		
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
		return ticketHistory;
	}

	@Transactional
	public Comment createComment(
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
		User commenter = userRepository.findByUsername(commenterUsername);
		comment.setCommenter(commenter);	
		commentRepository.save(comment);
		
		return comment;
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
    public Ticket assignTicketToDeveloper(
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
        ticket.setDeveloper(developer);
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
        	createTicketHistory("title", ticket.getTitle(),
        			title, id);
        	ticket.setTitle(title);
        }
        if(!ticket.getDescription().equals(description)) {
        	createTicketHistory("description", ticket.getDescription(),
        			description, id);
        	ticket.setDescription(description);
        }
        if(!ticket.getPriority().equals(priority)) {
        	createTicketHistory("priority", ticket.getPriority().toString(),
        			priority.toString(), id);
        	ticket.setPriority(priority);
        }
        if(!ticket.getStatus().equals(status)) {
        	createTicketHistory("status", ticket.getStatus().toString(),
        			status.toString(), id);
        	ticket.setStatus(status);
        }   
        if(!ticket.getType().equals(type)) {
        	createTicketHistory("type", ticket.getType().toString(),
        			type.toString(), id);
        	ticket.setType(type);
        } 
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
