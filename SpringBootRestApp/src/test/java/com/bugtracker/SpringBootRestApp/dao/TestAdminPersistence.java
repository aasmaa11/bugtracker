package com.bugtracker.SpringBootRestApp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bugtracker.SpringBootRestApp.dao.AdminRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAdminPersistence {
	@Autowired private AdminRepository adminRepository;
	
	@Autowired private ProjectRepository projectRepository;
	
    @AfterEach
    public void clearDatabase() {
        // Clearing the database

    	projectRepository.deleteAll();

        adminRepository.deleteAll();
        
    }
        
    /*    
    @Test
    public void testPersistAndLoadAdmin() {
        // Setting primitave attributes for customerAccount
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setEmail(email);

        // Save the object from the database
        adminRepository.save(admin);

        admin = null;

        // Load the object from the database
        admin = adminRepository.findByUsername(username);

        // Checking if attribute values of the saved and loaded object are the same
        assertNotNull(admin);
        assertEquals(username, admin.getUsername());
        assertEquals(password, admin.getPassword());
        assertEquals(email, admin.getEmail());
    }*/


}
