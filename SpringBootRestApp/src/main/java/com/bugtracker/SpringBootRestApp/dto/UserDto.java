package com.bugtracker.SpringBootRestApp.dto;

import java.util.List;

import com.bugtracker.SpringBootRestApp.model.Comment;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.model.TicketAttachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Long id;
	private List<CommentDto> comments;
	private List<TicketDto> createdTickets;
	private List<TicketAttachmentDto> ticketAttachments;
}
