package com.bugtracker.SpringBootRestApp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, String>{
	Ticket findById(int id);
	
	Ticket findByTicketAttachmentsId(int ticketAttachmentsId);
	
	Ticket findByTicketHistoriesId(int ticketHistoriesId);
	
	Ticket findByCommentsId(int commentsId);
	
	List<Ticket> findByDeveloperId(int developerId);
	
}
