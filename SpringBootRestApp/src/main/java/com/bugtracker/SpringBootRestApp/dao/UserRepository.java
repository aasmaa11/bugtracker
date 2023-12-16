package com.bugtracker.SpringBootRestApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.User;

public interface UserRepository extends CrudRepository<User, String>{
	User findById(int id);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User findByCommentsId(int commentsId);

}
