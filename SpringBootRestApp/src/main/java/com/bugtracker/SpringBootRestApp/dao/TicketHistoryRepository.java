package com.bugtracker.SpringBootRestApp.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.TicketHistory;

public interface TicketHistoryRepository extends CrudRepository<TicketHistory, String>{

	TicketHistory findById(int id);
	
	List<TicketHistory> findByTicketId(int ticketId);
}
