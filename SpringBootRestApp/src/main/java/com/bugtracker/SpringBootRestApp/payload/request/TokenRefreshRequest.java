package com.bugtracker.SpringBootRestApp.payload.request;

import jakarta.validation.constraints.NotBlank;

// Code from: https://www.bezkoder.com/spring-boot-refresh-token-jwt/
public class TokenRefreshRequest {
	@NotBlank
	  private String refreshToken;

	  public String getRefreshToken() {
	    return refreshToken;
	  }

	  public void setRefreshToken(String refreshToken) {
	    this.refreshToken = refreshToken;
	  }
}
