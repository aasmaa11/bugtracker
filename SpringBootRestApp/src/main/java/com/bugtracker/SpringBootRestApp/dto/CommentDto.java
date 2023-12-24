package com.bugtracker.SpringBootRestApp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	  private int id;
	  private String message;
	  private String creationDate;
	  private TicketDto ticket;
	  private UserDto commenter;
}
