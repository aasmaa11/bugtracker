package com.bugtracker.SpringBootRestApp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.dao.CommentRepository;
import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketAttachmentRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketHistoryRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Ticket;
import com.bugtracker.SpringBootRestApp.dao.ProjectManagerRepository;
import com.bugtracker.SpringBootRestApp.dao.AdminRepository;

import org.springframework.stereotype.Service;


@Service
public class ProjectService {

	
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
	public List<Project> getAllProjectsForDeveloper(String username){
		return toList(projectRepository.findByAssignedDevelopersUsername(username));
	}
	
	@Transactional
	public List<Project> getAllProjectsForProjectManager(String username){
		return toList(projectRepository.findByProjectManagerUsername(username));
	}
	
	@Transactional
	public Project getProject(int id) {
		return projectRepository.findById(id);
	}
	
	@Transactional
	public Project getProjectForTicket(int id) {
		Ticket ticket = ticketRepository.findById(id);
		return ticket.getProject();
	}
	
	@Transactional
	public Project createProject(
			String name,
			String description,
			String projectManagerUsername,
			Set<Developer> assignedDevelopers) {
		String error = "";
		if(name == null) {
			error += "Name cannot be empty";
		}
		if(projectManagerUsername == null) {
			error += "Project manager cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        if (!projectManagerRepository.existsByUsername(projectManagerUsername)) {
            throw new IllegalArgumentException("Project manager does not exist!");
        }
        ProjectManager projectManager = projectManagerRepository.findByUsername(projectManagerUsername);

    	Project project = new Project();
		project.setName(name);	
		project.setDescription(description);
		
		project.setProjectManager(projectManager);
		project.setAssignedDevelopers(assignedDevelopers);
		projectRepository.save(project);
		return project;
	}
	
	@Transactional
	public ProjectManager getProjectManagerForProject(Integer projectId) {
		String error = "";
		if(projectId == null) {
			error += "Project ID cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        ProjectManager projectManager = projectRepository.findById(projectId).getProjectManager();
        
		return projectManager;
        
	}

	@Transactional
	public List<Developer> getAllAssignedDevelopersForProject(int projectId){

        
		List<Developer> developers = toList(projectRepository.findById(projectId).getAssignedDevelopers()); 
		return developers;
	}
	
	@Transactional
	public Project modifyProject(
			Integer projectId,
			String name,
			String description) {
        
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        if(name != null) {
        	project.setName(name);	
        }
		if(description != null) {
			project.setDescription(description);
		}
		projectRepository.save(project);
		return project;
	}
	
	@Transactional 
	public Project assignProjectManagerToProject(
			Integer projectId,
			String projectManagerUsername) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        if (!projectManagerRepository.existsByUsername(projectManagerUsername)) {
            throw new IllegalArgumentException("Project manager does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        ProjectManager projectManager = projectManagerRepository.findByUsername(projectManagerUsername);
        project.setProjectManager(projectManager);
        projectRepository.save(project);
        return project;
	}
	
	@Transactional
	public Project addDeveloperToProject(
			Integer projectId,
			String username) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        if (!developerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Developer does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        Developer developer = developerRepository.findByUsername(username);
        
        if(project.getAssignedDevelopers() == null) {
        	Set<Developer> developers = new HashSet<Developer>();
        	developers.add(developer);
        	project.setAssignedDevelopers(developers);
        	
        }else {
        	Set<Developer> developers = project.getAssignedDevelopers();
        	developers.add(developer);
        	project.setAssignedDevelopers(developers);
        }
        projectRepository.save(project);
        return project;
	}
	
	@Transactional
	public Project setDevelopersToProject(
			Integer projectId,
			List<String> usernames) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        Set<Developer> developers = new HashSet<Developer>();
        for (String username: usernames) {
	        if (!developerRepository.existsByUsername(username)) {
	            throw new IllegalArgumentException("Developer does not exist!");
	        }
	        Developer developer = developerRepository.findByUsername(username);
	        developers.add(developer);
        }
        project.setAssignedDevelopers(developers);
        projectRepository.save(project);
        return project;
	}
	
	
	@Transactional
	public Project addDevelopersToProject(
			Integer projectId,
			List<String> usernames) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        System.out.println("Adding devs to project");
        Project project = projectRepository.findById(projectId);
        int i = 0;
        System.out.println(	"Checking if they are equals");
        System.out.println(developerRepository.findByUsername(usernames.get(0))
        		.equals(developerRepository.findByUsername(usernames.get(1))));
        
        for (String username: usernames) {
        	
        	
	        if (!developerRepository.existsByUsername(username)) {
	            throw new IllegalArgumentException("Developer does not exist!");
	        }
	        Developer developer = developerRepository.findByUsername(username);
	        System.out.println(developer.getUsername());
	        if(project.getAssignedDevelopers() == null) {
	        	Set<Developer> developers = new HashSet<Developer>();
	        	developers.add(developer);
	        	project.setAssignedDevelopers(developers);
	        	
	        }else {
	        	Set<Developer> developers = project.getAssignedDevelopers();
	        	System.out.println(	"devs already assigned");
	        	
	        	
	       		for(Developer d: developers) {
	        		System.out.println(d.getUsername());
	        	}
	       		System.out.println(	"end of small loop");
	        	System.out.println(developers.add(developer));
	        	project.setAssignedDevelopers(developers);
	        }
	        i++;
        }
        projectRepository.save(project);
        return project;
	}
	
    @Transactional
    public Project deleteProject(int id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        
        Project project = projectRepository.findById(id);
        projectRepository.delete(project);
        return project;
    }
    
    @Transactional
    public Project deleteTicketsForProject(int id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        List<Ticket> tickets = ticketRepository.findByProjectId(id);
        for(Ticket ticket: tickets) {
        	ticketRepository.delete(ticket);
        }
        Project project = projectRepository.findById(id);
        return project;
    }
	
    @Transactional
    public Project removeDeveloperFromProject(
			Integer projectId,
			String username) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        if (!developerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Developer does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        Developer developer = developerRepository.findByUsername(username);
        
        if(project.getAssignedDevelopers() != null) {
	        for (Developer d : project.getAssignedDevelopers()) {
	            if (d.getId() == developer.getId()) {
	            	project.getAssignedDevelopers().remove(d);
	                break;
	            }
	        }
        }
        
        projectRepository.save(project);
        return project;
    }
	
    
	@Transactional
	public List<Project> getAllProjects() {
		return toList(projectRepository.findAll());
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
