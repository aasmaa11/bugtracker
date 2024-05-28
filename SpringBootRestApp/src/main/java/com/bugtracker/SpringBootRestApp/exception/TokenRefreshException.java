package com.bugtracker.SpringBootRestApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// Code from: https://www.bezkoder.com/spring-boot-refresh-token-jwt/
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String token, String message) {
    super(String.format("Failed for [%s]: %s", token, message));
  }
}