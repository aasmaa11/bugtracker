package dao;

import org.springframework.data.repository.CrudRepository;

import model.User;

public interface UserRepository extends CrudRepository<User, String>{
	User findById(int id);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User findByCommentId(int commentId);

}
