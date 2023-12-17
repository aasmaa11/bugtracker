package com.bugtracker.SpringBootRestApp.dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, String>{
	Comment findById(int id);
	
	List<Comment> findByTicketId(int ticketId);
	
	List<Comment> findByCommenterId(int commenterId);

	boolean existsById(int id);

}