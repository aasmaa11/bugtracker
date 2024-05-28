package com.bugtracker.SpringBootRestApp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.dao.AdminRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectManagerRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAdminPersistence {
	@Autowired private AdminRepository adminRepository;
	
        
     
    @Test
    @Transactional
    public void testPersistAndLoadAdmin() {
        // Setting primitave attributes for customerAccount
        String username = "testName";
        String password = "12345678";
        String email = "test@gmail.com";
        String firstName = "first";
        String lastName = "last";

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setEmail(email);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);

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
        assertEquals(firstName, admin.getFirstName());
        assertEquals(lastName, admin.getLastName());
        
        adminRepository.delete(admin);
       
    }


}
