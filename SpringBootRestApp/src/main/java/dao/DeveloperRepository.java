package dao;

import org.springframework.data.repository.CrudRepository;

import model.Developer;

public interface DeveloperRepository extends CrudRepository<Developer, String>{
	Developer findByUsername(String username);
	
	Developer findByEmail(String email);
	
	Developer findById(int id);
	
	Developer findByTicketId(int ticketId);

}
