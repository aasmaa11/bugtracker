package com.bugtracker.SpringBootRestApp.payload.response;

import java.util.List;

// Code inspired from: https://www.bezkoder.com/spring-boot-security-postgresql-jwt-authentication/
// and from: https://www.bezkoder.com/spring-boot-refresh-token-jwt/
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String refreshToken, Long id, String username, String firstName, String lastName, String email,
			List<String> roles) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getRoles() {
		return roles;
	}
}