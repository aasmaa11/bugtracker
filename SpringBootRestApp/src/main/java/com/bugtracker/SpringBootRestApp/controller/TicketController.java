package com.bugtracker.SpringBootRestApp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

import com.bugtracker.SpringBootRestApp.controller.DtoConverterToLoad.Loads;
import com.bugtracker.SpringBootRestApp.dto.*;
import com.bugtracker.SpringBootRestApp.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
public class TicketController {
    @Autowired private TicketService ticketService;
    
    @Autowired private DtoConverterRestController converter;
    
    /*
     * Create ticket							DONE
     * Get ticket								DONE
     * Get all ticketAttachments for ticket		DONE
     * Get all ticketHistories for ticket		DONE
     * Get all tickets of developer				DONE
     * Get all tickets created by user			DONE
     * Get all tickets of project				DONE
     * Get all comments for ticket				DONE
     * Get all assigned developers for ticket	DONE
     * Modify ticket							DONE
     * Delete ticket							DONE
     * Delete ticket attachment					DONE
     * Delete comment							DONE
     * Add ticketAttachment						DONE
     * Add ticketHistory						DONE
     * Add assigned developer					DONE
     * Add comment								DONE
     * 
     * */
    
    
    private DtoConverterToLoad ticketLoads =
            new DtoConverterToLoad()
            		.put(Loads.TicketAttachments, null)
            		.put(Loads.TicketHistories, null)
            		.put(Loads.Comments, new DtoConverterToLoad().put(Loads.Commenter, null))
            		.put(Loads.Project, null)
                    .put(Loads.Submitter, null)
                    .put(Loads.AssignedDevelopers, null);
    
    @PostMapping(value = {"/ticket/createComment/{id}", "/ticket/createComment/{id}/"})
    public TicketDto addCommentToTicket(
    		@PathVariable("id") String id,
            @RequestParam("commenterUsername") String commenterUsername,
            @RequestParam("message") String message)
            throws IllegalArgumentException {
    	Ticket ticket = ticketService.createComment(message, Integer.parseInt(id), commenterUsername);
        return converter.convertToDto(ticket, ticketLoads);
    }
    
    @PostMapping(value = {"/ticket/createTa/{id}", "/ticket/createTa/{id}/"})
    public TicketDto addTicketAttachmentToTicket(
    		@PathVariable("id") String id,
            @RequestParam String creatorUsername,
            @RequestParam String file,
            @RequestParam String notes)
            throws IllegalArgumentException {
    	Ticket ticket = ticketService.addTicketAttachment(Integer.parseInt(id), file, notes, creatorUsername);
        return converter.convertToDto(ticket, ticketLoads);
    }
    
    @PostMapping(value = {"/ticket/createHistory/{id}", "/ticket/createHistory/{id}/"})
    public TicketDto addTicketHistoryToTicket(
    		@PathVariable("id") String id,
            @RequestParam String propertyChanged,
            @RequestParam String oldValue,
            @RequestParam String newValue)
            throws IllegalArgumentException {
    	Ticket ticket = ticketService.addTicketHistory(Integer.parseInt(id), propertyChanged, oldValue, newValue);
        return converter.convertToDto(ticket, ticketLoads);
    }
    
    @PostMapping(value = {"/ticket/assignDev/{username}", "/ticket/assignDev/{username}/"})
    public TicketDto addAssignedDeveloperToTicket(
    		@PathVariable("username") String username,
            @RequestParam int ticketId)
            throws IllegalArgumentException {
    	Ticket ticket = ticketService.addAssignedDeveloper(ticketId, username);
        return converter.convertToDto(ticket, ticketLoads);
    }
    
    @PostMapping(value = {"/ticket/assignDevs/{ticketId}", "/ticket/assignDev/{ticketId}/"})
    public TicketDto setAssignedDevelopersToTicket(
    		@PathVariable("ticketId") String ticketId,
    		@RequestParam("devUsernames") List<String> devUsernames)
            throws IllegalArgumentException {
    	Ticket ticket = ticketService.setAssignedDevelopers(Integer.parseInt(ticketId), devUsernames);
        return converter.convertToDto(ticket, ticketLoads);
    }
    
    
    @PostMapping(value = {"/ticket/project/{ticketId}", "/ticket/project/{ticketId}/"})
    public TicketDto setProjectToTicket(
    		@PathVariable("ticketId") String ticketId,
    		@RequestParam("projectId") String projectId)
            throws IllegalArgumentException {
    	Ticket ticket = ticketService.setProject(Integer.parseInt(ticketId),Integer.parseInt(projectId));
    	
        return converter.convertToDto(ticket, ticketLoads);
    }
    
    @PostMapping(value = {"/ticket/delete/{id}", "/ticket/delete/{id}/"})
    public TicketDto deleteTicket(@PathVariable("id") String id)
            throws IllegalArgumentException {
        return converter.convertToDto(ticketService.deleteTicket(Integer.parseInt(id)));
    }
    
    @PostMapping(value = {"/comment/delete/{id}", "/comment/delete/{id}/"})
    public CommentDto deleteComment(@PathVariable("id") String id)
            throws IllegalArgumentException {
        return converter.convertToDto(ticketService.deleteComment(Integer.parseInt(id)), ticketLoads);
    }
    
    @PostMapping(value = {"/ticketattachment/delete/{id}", "/ticketattachment/delete/{id}/"})
    public TicketAttachmentDto deleteTicketAttachment(@PathVariable("id") String id)
            throws IllegalArgumentException {
        return converter.convertToDto(ticketService.deleteTicketAttachment(Integer.parseInt(id)), ticketLoads);
    }
    
    @GetMapping(value = {"/ticket/{id}", "/ticket/{id}/"})
    public TicketDto getTicket(@PathVariable("id") String id) {
        return converter.convertToDto(ticketService.getTicket(Integer.parseInt(id)), ticketLoads);
    }
   
    
    @GetMapping(value = {"/ticket/attachments/{id}", "/ticket/attachments/{id}/"})
    public List<TicketAttachmentDto> getAllTicketAttachmentForTicket(@PathVariable("id") String id) {
        return ticketService.getAllTicketAttachmentForTicket(Integer.parseInt(id)).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/ticket/history/{id}", "/ticket/history/{id}/"})
    public List<TicketHistoryDto> getAllTicketHistoryForTicket(@PathVariable("id") String id) {
        return ticketService.getAllTicketHistoryForTicket(Integer.parseInt(id)).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/ticket/comments/{id}", "/ticket/comments/{id}/"})
    public List<CommentDto> getAllCommentForTicket(@PathVariable("id") String id) {
        return ticketService.getAllCommentForTicket(Integer.parseInt(id)).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/project/tickets/{id}", "/project/tickets/{id}/"})
    public List<TicketDto> getAllTicketsForProject(@PathVariable("id") String id) {
        return ticketService.getAllTicketsForProject(Integer.parseInt(id)).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/developer/tickets/{username}", "/developer/tickets/{username}/"})
    public List<TicketDto> getAllTicketsForDeveloperWithUsername(@PathVariable("username") String username) {
        return ticketService.getAllTicketsForDeveloperWithUsername(username).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/user/tickets/{username}", "/user/tickets/{username}/"})
    public List<TicketDto> getAllTicketsForSubmitterWithUsername(@PathVariable("username") String username) {
        return ticketService.getAllTicketsForSubmitterWithUsername(username).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/ticket/developers/{id}", "/ticket/developers/{id}/"})
    public List<DeveloperDto> getAllDevelopersForTicket(@PathVariable("id") String id) {
        return ticketService.getAllDevelopersForTicket(Integer.parseInt(id)).stream()
                .map(p -> converter.convertToDto(p, ticketLoads))
                .collect(Collectors.toList());
    }
    
    
    @PostMapping(value = {"/ticket/create", "/ticket/create/"})
    public TicketDto createTicket(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("priority") String priority,
            @RequestParam("status") String status,
            @RequestParam("type") String type,
            @RequestParam("projectId") String projectId,
            @RequestParam("submitterUsername") String submitterUsername)
            throws IllegalArgumentException {
    	Ticket.TicketPriority ticketPriority = null;
    	
    	if(priority.equals("None")) {
    		ticketPriority = Ticket.TicketPriority.None;
    	}else if(priority.equals("Low")) {
    		ticketPriority = Ticket.TicketPriority.Low;
    	}else if(priority.equals("Medium")) {
    		ticketPriority = Ticket.TicketPriority.Medium;
    	}else if(priority.equals("High")) {
    		ticketPriority = Ticket.TicketPriority.High;
    	}
    	Ticket.TicketStatus ticketStatus = null;
    	if(status.equals("New")) {
    		ticketStatus = Ticket.TicketStatus.New;
    	}else if(status.equals("Open")) {
    		ticketStatus = Ticket.TicketStatus.Open;
    	}else if(status.equals("InProgress")) {
    		ticketStatus = Ticket.TicketStatus.InProgress;
    	}else if(status.equals("AddInfo")) {
    		ticketStatus = Ticket.TicketStatus.AddInfo;
    	}else if(status.equals("Resolved")){
    		ticketStatus = Ticket.TicketStatus.Resolved;
    	}
    	
    	Ticket.TicketType ticketType = null;
    	if(type.equals("Bug")) {
    		ticketType = Ticket.TicketType.Bug;
    	}else if(type.equals("FeatureRequest")) {
    		ticketType = Ticket.TicketType.FeatureRequest;
    	}else if(type.equals("DocRequest")) {
    		ticketType = Ticket.TicketType.DocRequest;
    	}else if(type.equals("Other")){
    		ticketType = Ticket.TicketType.Other;
    	}
    	
    	Ticket ticket = ticketService.createTicket(title, description, ticketPriority, ticketStatus, ticketType, null, null, null, Integer.parseInt(projectId), submitterUsername);

        TicketDto ticketDto = converter.convertToDto(ticket, ticketLoads);
    	System.out.println("CHECK DTO INFOOOO");
        System.out.println(ticketDto.getProject().getProjectManager());
        return ticketDto;
    }
    
    @PostMapping(value = {"/ticket/modify/{id}", "/ticket/modify/{id}/"})
    public TicketDto modifyTicket(
    		@PathVariable("id") String id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("priority") String priority,
            @RequestParam("status") String status,
            @RequestParam("type") String type)
            throws IllegalArgumentException {
    	Ticket.TicketPriority ticketPriority = null;
    	
    	if(priority.equals("None")) {
    		ticketPriority = Ticket.TicketPriority.None;
    	}else if(priority.equals("Low")) {
    		ticketPriority = Ticket.TicketPriority.Low;
    	}else if(priority.equals("Medium")) {
    		ticketPriority = Ticket.TicketPriority.Medium;
    	}else if(priority.equals("High")) {
    		ticketPriority = Ticket.TicketPriority.High;
    	}
    	Ticket.TicketStatus ticketStatus = null;
    	if(status.equals("New")) {
    		ticketStatus = Ticket.TicketStatus.New;
    	}else if(status.equals("Open")) {
    		ticketStatus = Ticket.TicketStatus.Open;
    	}else if(status.equals("InProgress")) {
    		ticketStatus = Ticket.TicketStatus.InProgress;
    	}else if(status.equals("AddInfo")) {
    		ticketStatus = Ticket.TicketStatus.AddInfo;
    	}else if(status.equals("Resolved")){
    		ticketStatus = Ticket.TicketStatus.Resolved;
    	}
    	
    	Ticket.TicketType ticketType = null;
    	if(type.equals("Bug")) {
    		ticketType = Ticket.TicketType.Bug;
    	}else if(type.equals("FeatureRequest")) {
    		ticketType = Ticket.TicketType.FeatureRequest;
    	}else if(type.equals("DocRequest")) {
    		ticketType = Ticket.TicketType.DocRequest;
    	}else if(type.equals("Other")){
    		ticketType = Ticket.TicketType.Other;
    	}
    	
    	Ticket ticket = ticketService.modifyTicket(Integer.parseInt(id), title, description, ticketPriority, ticketStatus, ticketType);
        
        return converter.convertToDto(ticket, ticketLoads);
    }
}
