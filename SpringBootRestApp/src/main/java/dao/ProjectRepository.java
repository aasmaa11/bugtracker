package dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;

import model.Project;

public interface ProjectRepository extends CrudRepository<Project, String>{
	
	Project findById(int id);
	
	Project findByName(String name);
	
	List<Project> findByProjectManagerId(int projectManagerId);
	
	List<Project> findBySubmitterId(int submitterId);
	
	List<Project> findByAdminId(int adminId);
	
}
