package com.bugtracker.SpringBootRestApp.payload.response;


// Code from: https://www.bezkoder.com/spring-boot-security-postgresql-jwt-authentication/
public class MessageResponse {
	  private String message;

	  public MessageResponse(String message) {
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }
	}