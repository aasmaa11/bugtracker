package com.bugtracker.SpringBootRestApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, String>{
	boolean existsByUsername(String username);
	UserAccount findById(int id);
	
	UserAccount findByUsername(String username);
	
	UserAccount findByEmail(String email);
	
	UserAccount findByCommentsId(int commentsId);
	
	UserAccount findByCreatedTicketsId(int createdTicketsId);
	
	UserAccount findByTicketAttachmentsId(int ticketAttachmentsId);

}
