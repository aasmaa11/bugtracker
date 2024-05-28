package com.bugtracker.SpringBootRestApp.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.exception.TokenRefreshException;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.RefreshToken;
import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.payload.request.LoginRequest;
import com.bugtracker.SpringBootRestApp.payload.request.SignupRequest;
import com.bugtracker.SpringBootRestApp.payload.request.TokenRefreshRequest;
import com.bugtracker.SpringBootRestApp.payload.response.JwtResponse;
import com.bugtracker.SpringBootRestApp.payload.response.MessageResponse;
import com.bugtracker.SpringBootRestApp.payload.response.TokenRefreshResponse;
import com.bugtracker.SpringBootRestApp.security.jwt.JwtUtils;
import com.bugtracker.SpringBootRestApp.security.services.RefreshTokenService;
import com.bugtracker.SpringBootRestApp.security.services.UserDetailsImpl;
import com.bugtracker.SpringBootRestApp.service.UserService;


// Code inspired from: https://www.bezkoder.com/spring-boot-refresh-token-jwt/
// and from: https://www.bezkoder.com/spring-boot-security-postgresql-jwt-authentication/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;
	
	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(userDetails);

		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
			
		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
				userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		try {
			UserAccount user = null;
			Set<String> strRoles = signUpRequest.getRole();
			String role = null;

			if (strRoles == null) {
				throw new RuntimeException("Error: Role is not found.");
			} else {
				role = strRoles.iterator().next();
			}

			if(role.equals("Admin")) {
				user = userService.createAdmin(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(),
						signUpRequest.getLastName());
			}else if(role.equals("ProjectManager")) {
				user = userService.createProjectManager(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(),
						signUpRequest.getLastName());
			}else {
				user = userService.createDeveloper(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(),
						signUpRequest.getLastName());
			}


			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
		}

	}
	
	  @PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		  // get refresh token from request data
	    String requestRefreshToken = request.getRefreshToken();
	    
	    return refreshTokenService.findByToken(requestRefreshToken)
	            .map(refreshTokenService::verifyExpiration)
	            .map(RefreshToken::getUserAccount)
	            .map(user -> {
	              String token = jwtUtils.generateTokenFromUsername(user.getUsername());
	              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
	            })
	            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	                "Refresh token is not in database!"));

	  }
	  
	  
	  @PostMapping("/signout")
	  public ResponseEntity<?> logoutUser() {
	    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Long userId = userDetails.getId();
	    refreshTokenService.deleteByUserId(userId);
	    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	  }
}