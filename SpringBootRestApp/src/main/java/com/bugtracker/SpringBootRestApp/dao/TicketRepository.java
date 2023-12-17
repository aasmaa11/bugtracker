package com.bugtracker.SpringBootRestApp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, String>{
	Ticket findById(int id);
	
	Ticket findByTicketAttachmentsId(int ticketAttachmentsId);
	
	Ticket findByTicketHistoriesId(int ticketHistoriesId);
	List<Ticket> findByPriority(Ticket.TicketPriority priority);
	List<Ticket> findByStatus(Ticket.TicketStatus status);
	List<Ticket> findByType(Ticket.TicketType type);
	List<Ticket> findByProjectId(int projectId);
	
	
	Ticket findByCommentsId(int commentsId);
	
	List<Ticket> findByDeveloperId(int developerId);
	List<Ticket> findBySubmitterId(int submitterId);
	List<Ticket> findByDeveloperUsername(String developerUsername);

	boolean existsById(int id);
	
	
}
