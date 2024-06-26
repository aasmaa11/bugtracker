package com.bugtracker.SpringBootRestApp.dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Project;

public interface ProjectRepository extends CrudRepository<Project, String>{
	
	Project findById(int id);
	
	Project findByName(String name);
	
	List<Project> findByProjectManagerId(int projectManagerId);
	
	List<Project> findByProjectManagerUsername(String projectManagerUsername);
	
	List<Project> findByAssignedDevelopersUsername(String assignedDevelopersUsername);
	
	Project findByTicketsId(int ticketsId);

	boolean existsById(int projectId);
	
}
