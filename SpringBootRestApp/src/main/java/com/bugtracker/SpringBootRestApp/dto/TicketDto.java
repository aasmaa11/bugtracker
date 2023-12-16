package com.bugtracker.SpringBootRestApp.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDto {
	  public enum TicketStatus { New, Open, InProgress, Resolved, AddInfo }
	  public enum TicketType { Bug, FeatureRequest, DocRequest, Other }
	  public enum TicketPriority { None, Low, Medium, High }

	  private int id;
	  private String title;
	  private String description;
	  private TicketPriority priority;
	  private TicketStatus status;
	  private TicketType type;
	  private String creationDate;
	  private String updateDate;
	  private Set<TicketAttachmentDto> ticketAttachments;
	  private Set<TicketHistoryDto> ticketHistories;
	  private DeveloperDto developer;
	  private Set<CommentDto> comments;

}
