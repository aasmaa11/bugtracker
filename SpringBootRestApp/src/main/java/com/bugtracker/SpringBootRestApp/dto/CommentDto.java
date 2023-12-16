package com.bugtracker.SpringBootRestApp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
	  private int id;
	  private String message;
	  private String creationDate;
	  private TicketDto ticket;
	  private UserDto commenter;
}
