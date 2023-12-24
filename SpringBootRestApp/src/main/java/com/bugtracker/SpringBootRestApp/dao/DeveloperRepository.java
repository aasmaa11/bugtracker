package com.bugtracker.SpringBootRestApp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Developer;

public interface DeveloperRepository extends CrudRepository<Developer, String>{
	Developer findByUsername(String username);
	
	Developer findByEmail(String email);
	
	Developer findById(int id);
	
	List<Developer> findByAssignedTicketsId(int assignedTicketsId);
	
	List<Developer> findByProjectsId(int projectsId);

	boolean existsByUsername(String developerUsername);

}
