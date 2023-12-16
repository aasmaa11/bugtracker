package dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import model.TicketAttachment;

public interface TicketAttachmentRepository extends CrudRepository<TicketAttachment, String>{
	TicketAttachment findById(int id);
	
	List<TicketAttachment> findByTicketId(int ticketId);

}
