package com.bugtracker.SpringBootRestApp.dao;
import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, String>{
    Admin findByUsername(String username);
    
    Admin findById(Long id);

    Admin findByEmail(String email);

	boolean existsByUsername(String adminUsername);

}
