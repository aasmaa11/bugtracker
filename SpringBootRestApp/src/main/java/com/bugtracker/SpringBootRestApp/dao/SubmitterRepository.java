package com.bugtracker.SpringBootRestApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Submitter;

public interface SubmitterRepository extends CrudRepository<Submitter, String>{
	Submitter findById(int id);
	
	Submitter findByUsername(String username);
	
	Submitter findByEmail(String email);
	
	Submitter findByProjectsId(int projectsId);

}
