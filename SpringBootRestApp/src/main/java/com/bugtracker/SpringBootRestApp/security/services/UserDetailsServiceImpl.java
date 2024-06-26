package com.bugtracker.SpringBootRestApp.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;


// Code from: https://www.bezkoder.com/spring-boot-security-postgresql-jwt-authentication/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserAccountRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    if(!userRepository.existsByUsername(username)) {
    	throw new UsernameNotFoundException("User Not Found with username: " + username);
    }
    
    UserAccount user = userRepository.findByUsername(username);

    return UserDetailsImpl.build(user);
  }


}