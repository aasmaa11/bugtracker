package com.bugtracker.SpringBootRestApp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;
import com.bugtracker.SpringBootRestApp.model.TicketHistory;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTicketHistoryPersistence {
	@Autowired private TicketHistoryRepository ticketHistoryRepository;
	
	@Autowired private TicketRepository ticketRepository;
	
        
     
    @Test
    @Transactional
    public void testPersistAndLoadTicketHistory() {
        // Setting primitive attributes for customerAccount
        String propertyChanged = "test property";
        String oldValueOfProperty = "old";
        String newValueOfProperty = "new";

        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setPropertyChanged(propertyChanged);
        ticketHistory.setOldValueOfProperty(oldValueOfProperty);
        ticketHistory.setNewValueOfProperty(newValueOfProperty);
        // Save the object from the database
        ticketHistoryRepository.save(ticketHistory);
        int id = ticketHistory.getId();
        ticketHistory = null;

        // Load the object from the database
        ticketHistory = ticketHistoryRepository.findById(id);
        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(ticketHistory);
        assertEquals(propertyChanged, ticketHistory.getPropertyChanged());
        assertEquals(oldValueOfProperty, ticketHistory.getOldValueOfProperty());
        assertEquals(newValueOfProperty, ticketHistory.getNewValueOfProperty());
        ticketHistoryRepository.delete(ticketHistory);
    }
    
    @Test
    @Transactional
    public void testAssignTicket() {
		  String title = "test title";
		  String description = "test description";
		  TicketPriority priority = TicketPriority.High;
		  TicketStatus status = TicketStatus.InProgress;
		  TicketType type = TicketType.Bug;
	    	
		  Ticket ticket = new Ticket();
		  ticket.setTitle(title);
		  ticket.setDescription(description);
		  ticket.setPriority(priority);
		  ticket.setStatus(status);
		  ticket.setType(type);
		  
		  ticketRepository.save(ticket);
		  int ticketId = ticket.getId();
        // Setting primitave attributes for customerAccount
	        String propertyChanged = "test property";
	        String oldValueOfProperty = "old";
	        String newValueOfProperty = "new";

	        TicketHistory ticketHistory = new TicketHistory();
	        ticketHistory.setPropertyChanged(propertyChanged);
	        ticketHistory.setOldValueOfProperty(oldValueOfProperty);
	        ticketHistory.setNewValueOfProperty(newValueOfProperty);
	        ticketHistory.setTicket(ticket);
	        // Save the object from the database
	        ticketHistoryRepository.save(ticketHistory);
	        int id = ticketHistory.getId();
	        ticketHistory = null;

	        // Load the object from the database
	        ticketHistory = ticketHistoryRepository.findById(id);
	        // Checking if attribute values of the saved and loaded object are the same
	        assertNotNull(ticketHistory);
	        assertEquals(propertyChanged, ticketHistory.getPropertyChanged());
	        assertEquals(oldValueOfProperty, ticketHistory.getOldValueOfProperty());
	        assertEquals(newValueOfProperty, ticketHistory.getNewValueOfProperty());
        assertEquals(ticketId, ticketHistory.getTicket().getId());
        assertEquals(title, ticketHistory.getTicket().getTitle());
        assertEquals(description, ticketHistory.getTicket().getDescription());
        assertEquals(priority, ticketHistory.getTicket().getPriority());
        assertEquals(status, ticketHistory.getTicket().getStatus());
        assertEquals(type, ticketHistory.getTicket().getType());
        ticketHistoryRepository.delete(ticketHistory);
        
		  ticketRepository.delete(ticket);
    }
}
