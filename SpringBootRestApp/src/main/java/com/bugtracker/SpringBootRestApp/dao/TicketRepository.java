package com.bugtracker.SpringBootRestApp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, String>{
	Ticket findById(int id);
	List<Ticket> findByPriority(Ticket.TicketPriority priority);
	List<Ticket> findByStatus(Ticket.TicketStatus status);
	List<Ticket> findByType(Ticket.TicketType type);
	
	Ticket findByTicketAttachmentsId(int ticketAttachmentsId);
	Ticket findByTicketHistoriesId(int ticketHistoriesId);
	List<Ticket> findByProjectId(int projectId);
	Ticket findByCommentsId(int commentsId);
	
	List<Ticket> findByAssignedDevelopersId(int assignedDevelopersId);
	List<Ticket> findByAssignedDevelopersUsername(String assignedDevelopersUsername);
	List<Ticket> findBySubmitterId(int submitterId);
	List<Ticket> findBySubmitterUsername(String submitterUsername);

	boolean existsById(int id);
	
	
}
