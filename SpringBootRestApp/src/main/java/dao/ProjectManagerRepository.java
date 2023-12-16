package dao;

import org.springframework.data.repository.CrudRepository;

import model.ProjectManager;

public interface ProjectManagerRepository extends CrudRepository<ProjectManager, String>{
	ProjectManager findById(int id);
	
	ProjectManager findByUsername(String username);
	
	ProjectManager findByEmail(String email);
	
	
	ProjectManager findByProjectId(int projectId);

}
