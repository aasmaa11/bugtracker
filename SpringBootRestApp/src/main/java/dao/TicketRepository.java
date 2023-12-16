package dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, String>{
	Ticket findById(int id);
	
	Ticket findByTicketAttachmentId(int ticketAttachmentId);
	
	Ticket findByTicketHistoryId(int ticketHistoryId);
	
	Ticket findByCommentId(int commentId);
	
	List<Ticket> findByDeveloperId(int developerId);
	
}
