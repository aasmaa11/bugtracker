package com.bugtracker.SpringBootRestApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Developer;

public interface DeveloperRepository extends CrudRepository<Developer, String>{
	Developer findByUsername(String username);
	
	Developer findByEmail(String email);
	
	Developer findById(int id);
	
	Developer findByTicketsId(int ticketsId);

	boolean existsByUsername(String developerUsername);

}
