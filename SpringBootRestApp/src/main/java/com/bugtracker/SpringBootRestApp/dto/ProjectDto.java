package com.bugtracker.SpringBootRestApp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
	private int id;
	private String name;
	private String description;

	private ProjectManagerDto projectManager;
	private List<DeveloperDto> assignedDevelopers;
	private List<TicketDto> tickets;
}
