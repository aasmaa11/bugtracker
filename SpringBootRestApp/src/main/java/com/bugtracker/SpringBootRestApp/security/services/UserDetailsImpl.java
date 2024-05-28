package com.bugtracker.SpringBootRestApp.security.services;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.fasterxml.jackson.annotation.JsonIgnore;


// Code inspired from: https://www.bezkoder.com/spring-boot-security-postgresql-jwt-authentication/
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;
  private String email;
  private String firstName;
  private String lastName;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String firstName, String lastName, String email, String password,
		  Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(UserAccount user) {
	  
	  String roleName = null;
	  if(user instanceof Admin) {
		  roleName = "ROLE_ADMIN";
	  }else if(user instanceof ProjectManager) {
		  roleName = "ROLE_PROJECTMANAGER";
	  }else {
		  roleName = "ROLE_DEVELOPER";
	  }
	  GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
	  System.out.println(authority);
	  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(Collections.singletonList(authority));
	  System.out.println(authorities);
    return new UserDetailsImpl(user.getId(), 
                               user.getUsername(), 
                               user.getFirstName(),
                               user.getLastName(),
                               user.getEmail(),
                               user.getPassword(), 
                               authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }
  
  public String getFirstName() {
	    return firstName;
	  }
  
  public String getLastName() {
	    return lastName;
	  }


  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}