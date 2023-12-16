package com.bugtracker.SpringBootRestApp.dto;

import java.util.Set;

import com.bugtracker.SpringBootRestApp.model.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	  private String username;
	  private String email;
	  private String password;
	  private int id;
	  private Set<CommentDto> comments;
}
