package com.bugtracker.SpringBootRestApp.dto;


import com.bugtracker.SpringBootRestApp.model.Ticket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketHistoryDto {
	  private int id;
	  private String propertyChanged;
	  private String oldValueOfProperty;
	  private String newValueOfProperty;
	  private String dateOfChange;
	  private TicketDto ticket;

}
