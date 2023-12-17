package com.bugtracker.SpringBootRestApp.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDto {
	private Set<ProjectDto> projects;
}