package com.bugtracker.SpringBootRestApp.dto;

import java.util.List;

import com.bugtracker.SpringBootRestApp.model.Ticket.TicketPriority;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketStatus;
import com.bugtracker.SpringBootRestApp.model.Ticket.TicketType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

	  private int id;
	  private String title;
	  private String description;
	  private TicketPriority priority;
	  private TicketStatus status;
	  private TicketType type;
	  private String creationDate;
	  private String updateDate;

	  private List<TicketAttachmentDto> ticketAttachments;
	  private List<TicketHistoryDto> ticketHistories;
	  private List<DeveloperDto> assignedDevelopers;
	  private List<CommentDto> comments;
	  private ProjectDto project;
	  private UserDto submitter;

}
