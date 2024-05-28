package com.bugtracker.SpringBootRestApp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bugtracker.SpringBootRestApp.dao.AdminRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;


// Code inspired from: https://stackoverflow.com/questions/52370437/create-admin-account-by-default-in-spring-boot
@Component    
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired AdminRepository adminRepository;
    
	@Autowired
	PasswordEncoder encoder;
    
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;
    
    @Value("${admin.firstName}")
    private String adminFirstName;
    
    @Value("${admin.lastName}")
    private String adminLastName;
    
    @Value("${admin.email}")
    private String adminEmail;

    @Override
    public void run(String...args) throws Exception {
    	if(!adminRepository.existsByUsername(adminUsername)) {
        	Admin admin = new Admin();
        	admin.setUsername(adminUsername);
        	admin.setPassword(encoder.encode(adminPassword));
        	admin.setFirstName(adminFirstName);
        	admin.setLastName(adminLastName);
        	admin.setEmail(adminEmail);
        	adminRepository.save(admin);
    	}

    }
}