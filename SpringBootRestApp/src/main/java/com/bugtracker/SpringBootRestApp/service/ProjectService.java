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
import com.bugtracker.SpringBootRestApp.dao.SubmitterRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketAttachmentRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketHistoryRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Submitter;
import com.bugtracker.SpringBootRestApp.dao.ProjectManagerRepository;
import com.bugtracker.SpringBootRestApp.dao.AdminRepository;

public class ProjectService {
	
	@Autowired TicketRepository ticketRepository;
	@Autowired TicketAttachmentRepository ticketAttachmentRepository;
	@Autowired TicketHistoryRepository ticketHistoryRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired DeveloperRepository developerRepository;
	@Autowired ProjectRepository projectRepository;
	@Autowired SubmitterRepository submitterRepository;
	@Autowired UserRepository userRepository;
	@Autowired AdminRepository adminRepository;
	@Autowired ProjectManagerRepository projectManagerRepository;
	
	
	@Transactional
	public Project createProject(
			String name,
			String description,
			String projectManagerUsername,
			Set<Submitter> submitters,
			String adminUsername) {
		String error = "";
		if(name == null) {
			error += "Name cannot be empty";
		}
		if(projectManagerUsername == null) {
			error += "Project manager cannot be empty";
		}
		if(adminUsername == null) {
			error += "Admin cannot be empty";
		}
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        if (!projectManagerRepository.existsByUsername(projectManagerUsername)) {
            throw new IllegalArgumentException("Project manager does not exist!");
        }
        if (!adminRepository.existsByUsername(adminUsername)) {
            throw new IllegalArgumentException("Admin does not exist!");
        }
        ProjectManager projectManager = projectManagerRepository.findByUsername(projectManagerUsername);
        Admin admin = adminRepository.findByUsername(adminUsername);
    			
        Project project = new Project();
		project.setName(name);	
		project.setDescription(description);
		project.setProjectManager(projectManager);
		project.setSubmitters(submitters);
		project.setAdmin(admin);
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
		ProjectManager projectManager = projectManagerRepository.findByProjectsId(projectId);
		return projectManager;
        
	}

	@Transactional
	public List<Submitter> getAllSubmittersForProject(int projectId){
		return toList(submitterRepository.findByProjectsId(projectId));
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
	public Project addSubmitterToProject(
			Integer projectId,
			String submitterUsername) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        if (!submitterRepository.existsByUsername(submitterUsername)) {
            throw new IllegalArgumentException("Submitter does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        Submitter submitter = submitterRepository.findByUsername(submitterUsername);
        
        if(project.getSubmitters() == null) {
        	Set<Submitter> submitters = new HashSet<Submitter>();
        	submitters.add(submitter);
        	project.setSubmitters(submitters);
        	
        }else {
        	project.getSubmitters().add(submitter);
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
    public Project removeSubmitterFromProject(
			Integer projectId,
			String submitterUsername) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist!");
        }
        if (!submitterRepository.existsByUsername(submitterUsername)) {
            throw new IllegalArgumentException("Submitter does not exist!");
        }
        Project project = projectRepository.findById(projectId);
        Submitter submitter = submitterRepository.findByUsername(submitterUsername);
        
        if(project.getSubmitters() != null) {
	        for (Submitter s : project.getSubmitters()) {
	            if (s.getId() == submitter.getId()) {
	            	project.getSubmitters().remove(s);
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
