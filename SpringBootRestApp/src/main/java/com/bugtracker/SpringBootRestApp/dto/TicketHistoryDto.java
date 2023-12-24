package com.bugtracker.SpringBootRestApp.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketHistoryDto {
	  private int id;
	  private String propertyChanged;
	  private String oldValueOfProperty;
	  private String newValueOfProperty;
	  private String dateOfChange;
	  private TicketDto ticket;

}
