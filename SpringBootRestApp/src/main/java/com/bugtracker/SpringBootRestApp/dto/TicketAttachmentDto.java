package com.bugtracker.SpringBootRestApp.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketAttachmentDto {

	  private int id;
	  private String file;
	  private String notes;
	  private String creationDate;
	  private TicketDto ticket;
}
