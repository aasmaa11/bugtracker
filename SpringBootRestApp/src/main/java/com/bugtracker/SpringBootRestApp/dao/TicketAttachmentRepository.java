package com.bugtracker.SpringBootRestApp.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.TicketAttachment;

public interface TicketAttachmentRepository extends CrudRepository<TicketAttachment, String>{
	TicketAttachment findById(int id);
	
	List<TicketAttachment> findByTicketId(int ticketId);
	
	List<TicketAttachment> findByCreatorId(int creatorId);
	
	List<TicketAttachment> findByCreatorUsername(String creatorUsername);

	boolean existsById(int id);

}
