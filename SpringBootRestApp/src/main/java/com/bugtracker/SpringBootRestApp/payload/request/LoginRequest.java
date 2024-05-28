package com.bugtracker.SpringBootRestApp.payload.request;

import jakarta.validation.constraints.NotBlank;

// Code from: https://www.bezkoder.com/spring-boot-security-postgresql-jwt-authentication/
public class LoginRequest {
  @NotBlank
  private String username;

  @NotBlank
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}