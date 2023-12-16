package com.bugtracker.SpringBootRestApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.ProjectManager;

public interface ProjectManagerRepository extends CrudRepository<ProjectManager, String>{
	ProjectManager findById(int id);
	
	ProjectManager findByUsername(String username);
	
	ProjectManager findByEmail(String email);
	
	
	ProjectManager findByProjectsId(int projectsId);

}
