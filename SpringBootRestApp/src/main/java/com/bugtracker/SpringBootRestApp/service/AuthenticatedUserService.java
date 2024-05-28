package com.bugtracker.SpringBootRestApp.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bugtracker.SpringBootRestApp.security.services.UserDetailsImpl;


// Inspired from: https://stackoverflow.com/questions/52757368/spring-security-allow-each-user-to-see-their-own-profile-but-none-else
@Service
public class AuthenticatedUserService {


public boolean isMyPage(String username) {
	UserDetailsImpl userDetails = (UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	  if(userDetails.getUsername().equals(username)) {
		  return true;
	  }
	  return false;
}

}
