package dao;
import org.springframework.data.repository.CrudRepository;

import model.Admin;

public interface AdminRepository extends CrudRepository<Admin, String>{
    Admin findByUsername(String username);
    
    Admin findById(int id);

    Admin findByEmail(String email);
    
    Admin findByProjectId(int projectId);

}
