package dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;

import model.Comment;

public interface CommentRepository extends CrudRepository<Comment, String>{
	Comment findById(int id);
	
	List<Comment> findByTicketId(int ticketId);
	
	List<Comment> findByCommenterId(int commenterId);

}
