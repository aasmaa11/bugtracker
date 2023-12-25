package com.bugtracker.SpringBootRestApp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectManagerDto extends UserDto{
	private List<ProjectDto> projects;
}
