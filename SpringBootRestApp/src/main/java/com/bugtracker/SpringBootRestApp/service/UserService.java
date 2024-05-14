package com.bugtracker.SpringBootRestApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.dao.AdminRepository;
import com.bugtracker.SpringBootRestApp.dao.CommentRepository;
import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectManagerRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketAttachmentRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketHistoryRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.UserAccount;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	public enum UserRole { Admin, ProjectManager, Developer }
	
	@Autowired TicketRepository ticketRepository;
	@Autowired TicketAttachmentRepository ticketAttachmentRepository;
	@Autowired TicketHistoryRepository ticketHistoryRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired DeveloperRepository developerRepository;
	@Autowired ProjectRepository projectRepository;
	@Autowired UserAccountRepository userRepository;
	@Autowired AdminRepository adminRepository;
	@Autowired ProjectManagerRepository projectManagerRepository;
	
	@Transactional
	public Admin createAdmin(
			String username,
			String email,
			String password,
			String firstName,
			String lastName) {

		String error = "";
		if(username == null) {
			error += "Username cannot be empty";
		}
		if(email == null) {
			error += "Email cannot be empty";
		}
		if(password == null) {
			error += "Password cannot be empty";
		}
		if(firstName == null) {
			error += "First name cannot be empty";
		}
		if(lastName == null) {
			error += "Last name cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		if(userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already exists!");
		}
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setEmail(email);
		admin.setPassword(password);
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		adminRepository.save(admin);
		return admin;
	}
	

	@Transactional
	public ProjectManager createProjectManager(
			String username,
			String email,
			String password,
			String firstName,
			String lastName) {

		String error = "";
		if(username == null) {
			error += "Username cannot be empty";
		}
		if(email == null) {
			error += "Email cannot be empty";
		}
		if(password == null) {
			error += "Password cannot be empty";
		}
		if(firstName == null) {
			error += "First name cannot be empty";
		}
		if(lastName == null) {
			error += "Last name cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		if(userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already exists!");
		}
		ProjectManager projectManager = new ProjectManager();
		projectManager.setUsername(username);
		projectManager.setEmail(email);
		projectManager.setPassword(password);
		projectManager.setFirstName(firstName);
		projectManager.setLastName(lastName);
		projectManagerRepository.save(projectManager);
		return projectManager;
	}

	@Transactional
	public Developer createDeveloper(
			String username,
			String email,
			String password,
			String firstName,
			String lastName) {

		String error = "";
		if(username == null) {
			error += "Username cannot be empty";
		}
		if(email == null) {
			error += "Email cannot be empty";
		}
		if(password == null) {
			error += "Password cannot be empty";
		}
		if(firstName == null) {
			error += "First name cannot be empty";
		}
		if(lastName == null) {
			error += "Last name cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		if(userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already exists!");
		}
		Developer developer = new Developer();
		developer.setUsername(username);
		developer.setEmail(email);
		developer.setPassword(password);
		developer.setFirstName(firstName);
		developer.setLastName(lastName);
		developerRepository.save(developer);
		return developer;
	}
	
	@Transactional
	public UserRole getUserRole(String username) {
		if(username == null) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if(!userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("UserAccount does not exist!");
		}
		
		UserAccount user = userRepository.findByUsername(username);
		if(user instanceof Admin) {
			return UserRole.Admin;
		}
		if(user instanceof ProjectManager) {
			return UserRole.ProjectManager;
		}
		return UserRole.Developer;	
	}
	
	
	
	@Transactional
	public UserAccount changeUserRole(String username, UserRole userRole) {
		String error = "";
		if(username == null) {
			error += "Username cannot be empty";
		}
		if(userRole == null) {
			error += "UserAccount role cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		UserAccount user = userRepository.findByUsername(username);
		if(user == null) {
			throw new IllegalArgumentException("UserAccount does not exist!");
		}
		UserAccount newUser = user;
		if(userRole.equals(UserRole.Admin) && !(user instanceof Admin)) {
			userRepository.delete(user);
			newUser = createAdmin(user.getUsername(), user.getEmail(), user.getPassword(),
					user.getFirstName(), user.getLastName());
			
		}else if(userRole.equals(UserRole.ProjectManager) && !(user instanceof ProjectManager)) {
			userRepository.delete(user);
			newUser = createProjectManager(user.getUsername(), user.getEmail(), user.getPassword(),
					user.getFirstName(), user.getLastName());
			
		}else if(userRole.equals(UserRole.Developer) && !(user instanceof Developer)){
			userRepository.delete(user);
			newUser = createDeveloper(user.getUsername(), user.getEmail(), user.getPassword(),
					user.getFirstName(), user.getLastName());
			
		}
		
		return newUser;
	}

    @Transactional
    public Admin deleteAdmin(String username) {
        if (!adminRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Admin does not exist!");
        }
        
        Admin admin = adminRepository.findByUsername(username);
        adminRepository.delete(admin);
        return admin;
    }
	
    @Transactional
    public ProjectManager deleteProjectManager(String username) {
        if (!projectManagerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Project manager does not exist!");
        }
        
        ProjectManager projectManager = projectManagerRepository.findByUsername(username);
        projectManagerRepository.delete(projectManager);
        return projectManager;
    }
    

    
    @Transactional
    public Developer deleteDeveloper(String username) {
        if (!developerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Developer does not exist!");
        }
        
        Developer developer = developerRepository.findByUsername(username);
        developerRepository.delete(developer);
        return developer;
    }
	
    @Transactional 
    public UserAccount modifyUser(
    		String username,
    		String newUsername,
    		String newEmail,
    		String newPassword,
    		String newFirstName,
    		String newLastName) {

        if (!userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("UserAccount does not exist!");
        }
        UserAccount user = userRepository.findByUsername(username);
        if(newUsername != null) {
        	if(!username.equals(newUsername)) {
                if (userRepository.existsByUsername(newUsername)) {
                    throw new IllegalArgumentException("Username already exists");
                }else {
                	user.setUsername(newUsername);;
                }  
        	}
        }
        if(newEmail != null) {
        	user.setEmail(newEmail);        
        }
        if(newPassword != null) {
        	user.setPassword(newPassword);
        }
        if(newFirstName != null) {
        	user.setFirstName(newFirstName);        
        }
        if(newLastName != null) {
        	user.setLastName(newLastName);       
        }
        userRepository.save(user);
        return user;
        
    }
    
    
    @Transactional
    public Admin getAdmin(String username) {
        if (!adminRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Admin does not exist!");
        }
        Admin admin = adminRepository.findByUsername(username);
        return admin;
    }
    
    @Transactional
    public ProjectManager getProjectManager(String username) {
        if (!projectManagerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Project manager does not exist!");
        }
        
        ProjectManager projectManager = projectManagerRepository.findByUsername(username);
        
        return projectManager;
    }

    
    @Transactional
    public Developer getDeveloper(String username) {
        if (!developerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Developer does not exist!");
        }
        
        Developer developer = developerRepository.findByUsername(username);
        
        return developer;
    }
    
    @Transactional
    public UserAccount getUser(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User does not exist!");
        }
        
        UserAccount user = userRepository.findByUsername(username);
        
        return user;
    }

	@Transactional
	public List<UserAccount> getAllUsers() {
		return toList(userRepository.findAll());
	}
	
	

	@Transactional
	public List<Developer> getAllDevelopers() {
		return toList(developerRepository.findAll());
	}
	
	@Transactional
	public List<ProjectManager> getAllProjectManagers() {
		return toList(projectManagerRepository.findAll());
	}
	
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        if (iterable != null) {
            for (T t : iterable) {
                resultList.add(t);
            }
        }
        return resultList;
    }

}
