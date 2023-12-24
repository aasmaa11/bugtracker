package com.bugtracker.SpringBootRestApp.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectManagerDto extends UserDto{
	private Set<ProjectDto> projects;
}
