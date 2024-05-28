package com.bugtracker.SpringBootRestApp.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Code inspired from: https://www.bezkoder.com/spring-boot-refresh-token-jwt/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;

	  @OneToOne
	  private UserAccount userAccount;

	  private String token;

	  private Instant expiryDate;

}
