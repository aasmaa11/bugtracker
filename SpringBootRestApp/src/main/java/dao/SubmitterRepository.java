package dao;

import org.springframework.data.repository.CrudRepository;

import model.Submitter;

public interface SubmitterRepository extends CrudRepository<Submitter, String>{
	Submitter findById(int id);
	
	Submitter findByUsername(String username);
	
	Submitter findByEmail(String email);
	
	Submitter findByProjectId(int projectId);

}
