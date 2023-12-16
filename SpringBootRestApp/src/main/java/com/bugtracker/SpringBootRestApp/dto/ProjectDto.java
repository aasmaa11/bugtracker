package com.bugtracker.SpringBootRestApp.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
	  private String name;
	  private String description;
	  private int id;

	  private ProjectManagerDto projectManager;
	  private Set<SubmitterDto> submitters;
	  private AdminDto admin;
}
