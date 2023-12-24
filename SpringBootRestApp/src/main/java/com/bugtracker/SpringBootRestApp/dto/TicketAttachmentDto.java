package com.bugtracker.SpringBootRestApp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketAttachmentDto {

	private int id;
	private String file;
	private String notes;
	private String creationDate;
	private TicketDto ticket;
	private UserDto creator;
}
