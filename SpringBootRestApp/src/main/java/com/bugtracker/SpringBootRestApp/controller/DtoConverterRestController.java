package com.bugtracker.SpringBootRestApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.controller.DtoConverterToLoad.Loads;
import com.bugtracker.SpringBootRestApp.dto.*;
import com.bugtracker.SpringBootRestApp.model.*;
import com.bugtracker.SpringBootRestApp.service.ProjectService;
import com.bugtracker.SpringBootRestApp.service.TicketService;
import com.bugtracker.SpringBootRestApp.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class DtoConverterRestController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private UserService userService;
	
	// DOMAIN MODEL TO DTO CONVERTER

	public AdminDto convertToDto(Admin a) {
		return convertToDto(a, null);
	}

	public AdminDto convertToDto(Admin a, DtoConverterToLoad toLoad) {
		if (a == null) {
			return null;
		}
		AdminDto adminDto = new AdminDto();
		adminDto.setUsername(a.getUsername());
		adminDto.setEmail(a.getEmail());
		adminDto.setPassword(a.getPassword());
		adminDto.setFirstName(a.getFirstName());
		adminDto.setLastName(a.getLastName());

		if (toLoad != null) {
			if (toLoad.has(Loads.Comments)) {
				List<Comment> comments = ticketService.getAllCommentForUser(a.getUsername());
				adminDto.setComments(convertToDto(comments, toLoad.get(Loads.Comments)));
			}
			if (toLoad.has(Loads.CreatedTickets)) {
				List<Ticket> tickets = ticketService.getAllTicketsForSubmitterWithUsername(a.getUsername());
				adminDto.setCreatedTickets(convertToDto(tickets, toLoad.get(Loads.CreatedTickets)));
		
			}if (toLoad.has(Loads.TicketAttachments)) {
				List<TicketAttachment> ticketAttachments = ticketService.getAllTicketAttachmentForUser(a.getUsername());
				adminDto.setTicketAttachments(convertToDto(ticketAttachments, toLoad.get(Loads.TicketAttachments)));
			}
		}
		return adminDto;
	}
	
	public CommentDto convertToDto(Comment c) {
		return convertToDto(c, null);
	}
	
	public CommentDto convertToDto(Comment c, DtoConverterToLoad toLoad) {
		if (c == null) {
			return null;
		}
		CommentDto commentDto = new CommentDto(c.getId(), c.getMessage(), c.getCreationDate(), null, null);

		if (toLoad != null) {
			if (toLoad.has(Loads.Ticket)) {
				
				Ticket ticket = ticketService.getTicketOfComment(c.getId());
				commentDto.setTicket(convertToDto(ticket, toLoad.get(Loads.Ticket)));
			}
			if (toLoad.has(Loads.Commenter)) {
				UserAccount commenter = ticketService.getCommenterForComment(c.getId());
				commentDto.setCommenter(convertToDto(commenter, toLoad.get(Loads.Commenter)));
		
			}
		}
		return commentDto;
	}
	
	public DeveloperDto convertToDto(Developer d) {
		return convertToDto(d, null);
	}

	public DeveloperDto convertToDto(Developer d, DtoConverterToLoad toLoad) {
		if (d == null) {
			return null;
		}
		DeveloperDto developerDto = new DeveloperDto();
		developerDto.setUsername(d.getUsername());
		developerDto.setEmail(d.getEmail());
		developerDto.setPassword(d.getPassword());
		developerDto.setFirstName(d.getFirstName());
		developerDto.setLastName(d.getLastName());

		if (toLoad != null) {
			if (toLoad.has(Loads.Comments)) {
				List<Comment> comments = ticketService.getAllCommentForUser(d.getUsername());
				developerDto.setComments(convertToDto(comments, toLoad.get(Loads.Comments)));
			}
			if (toLoad.has(Loads.CreatedTickets)) {
				List<Ticket> tickets = ticketService.getAllTicketsForSubmitterWithUsername(d.getUsername());
				developerDto.setCreatedTickets(convertToDto(tickets, toLoad.get(Loads.CreatedTickets)));
		
			}if (toLoad.has(Loads.TicketAttachments)) {
				List<TicketAttachment> ticketAttachments = ticketService.getAllTicketAttachmentForUser(d.getUsername());
				developerDto.setTicketAttachments(convertToDto(ticketAttachments, toLoad.get(Loads.TicketAttachments)));
				
			}if (toLoad.has(Loads.AssignedTickets)) {
				List<Ticket> tickets = ticketService.getAllTicketsForDeveloperWithUsername(d.getUsername());
				developerDto.setTicketAttachments(convertToDto(tickets, toLoad.get(Loads.AssignedTickets)));
				
			}if (toLoad.has(Loads.Projects)) {
				List<Project> projects = projectService.getAllProjectsForDeveloper(d.getUsername());
				developerDto.setTicketAttachments(convertToDto(projects, toLoad.get(Loads.Projects)));
				
			}
		}
		return developerDto;
	}
	
	public ProjectDto convertToDto(Project p) {
		return convertToDto(p, null);
	}

	public ProjectDto convertToDto(Project p, DtoConverterToLoad toLoad) {
		if (p == null) {
			return null;
		}
		ProjectDto projectDto = new ProjectDto(p.getId(), p.getName(), p.getDescription(), null, null, null);
		if (toLoad != null) {
			if (toLoad.has(Loads.ProjectManager)) {
				ProjectManager projectManager = projectService.getProjectManagerForProject(p.getId());
				projectDto.setProjectManager(convertToDto(projectManager, toLoad.get(Loads.ProjectManager)));
			}
			if (toLoad.has(Loads.AssignedDevelopers)) {
				List<Developer> developers = projectService.getAllAssignedDevelopersForProject(p.getId());
				projectDto.setAssignedDevelopers(convertToDto(developers, toLoad.get(Loads.AssignedDevelopers)));
		
			}if (toLoad.has(Loads.Tickets)) {
				List<Ticket> tickets = ticketService.getAllTicketsForProject(p.getId());
				projectDto.setTickets(convertToDto(tickets, toLoad.get(Loads.Tickets)));
				
			}
		}
		return projectDto;
	}
	
	
	public ProjectManagerDto convertToDto(ProjectManager p) {
		return convertToDto(p, null);
	}

	public ProjectManagerDto convertToDto(ProjectManager p, DtoConverterToLoad toLoad) {
		if (p == null) {
			return null;
		}
		ProjectManagerDto projectManager = new ProjectManagerDto();
		projectManager.setUsername(p.getUsername());
		projectManager.setEmail(p.getEmail());
		projectManager.setPassword(p.getPassword());
		projectManager.setFirstName(p.getFirstName());
		projectManager.setLastName(p.getLastName());

		if (toLoad != null) {
			if (toLoad.has(Loads.Comments)) {
				List<Comment> comments = ticketService.getAllCommentForUser(p.getUsername());
				projectManager.setComments(convertToDto(comments, toLoad.get(Loads.Comments)));
			}
			if (toLoad.has(Loads.CreatedTickets)) {
				List<Ticket> tickets = ticketService.getAllTicketsForSubmitterWithUsername(p.getUsername());
				projectManager.setCreatedTickets(convertToDto(tickets, toLoad.get(Loads.CreatedTickets)));
		
			}if (toLoad.has(Loads.TicketAttachments)) {
				List<TicketAttachment> ticketAttachments = ticketService.getAllTicketAttachmentForUser(p.getUsername());
				projectManager.setTicketAttachments(convertToDto(ticketAttachments, toLoad.get(Loads.TicketAttachments)));
				
			}if (toLoad.has(Loads.Projects)) {
				List<Project> projects = projectService.getAllProjectsForProjectManager(p.getUsername());
				projectManager.setProjects(convertToDto(projects, toLoad.get(Loads.Projects)));
				
			}
		}
		return projectManager;
	}
	
	
	public TicketAttachmentDto convertToDto(TicketAttachment t) {
		return convertToDto(t, null);
	}
	
	public TicketAttachmentDto convertToDto(TicketAttachment t, DtoConverterToLoad toLoad) {
		if (t == null) {
			return null;
		}
		TicketAttachmentDto ticketAttachmentDto = new TicketAttachmentDto(t.getId(), t.getFile(), t.getNotes(), t.getCreationDate(), null, null);

		if (toLoad != null) {
			if (toLoad.has(Loads.Ticket)) {
				
				Ticket ticket = ticketService.getTicketForTicketAttachment(t.getId());
				ticketAttachmentDto.setTicket(convertToDto(ticket, toLoad.get(Loads.Ticket)));
			}
			if (toLoad.has(Loads.Creator)) {
				UserAccount creator = ticketService.getCreatorForTicketAttachment(t.getId());
				ticketAttachmentDto.setCreator(convertToDto(creator, toLoad.get(Loads.Creator)));
		
			}
		}
		return ticketAttachmentDto;
	}
	
	public TicketDto convertToDto(Ticket t) {
		return convertToDto(t, null);
	}
	
	public TicketDto convertToDto(Ticket t, DtoConverterToLoad toLoad) {
		if (t == null) {
			return null;
		}
		TicketDto ticketDto = new TicketDto(t.getId(), t.getTitle(), t.getDescription(), t.getPriority(),
				t.getStatus(), t.getType(), t.getCreationDate(), t.getUpdateDate(), null, null, null, null, null, null);
		
		if (toLoad != null) {
			if (toLoad.has(Loads.TicketAttachments)) {
				
				List<TicketAttachment> ticketAttachments = ticketService.getAllTicketAttachmentForTicket(t.getId());
				ticketDto.setTicketAttachments(convertToDto(ticketAttachments, toLoad.get(Loads.TicketAttachments)));;
				
			}
			if (toLoad.has(Loads.TicketHistories)) {
				List<TicketHistory> ticketHistories = ticketService.getAllTicketHistoryForTicket(t.getId());
				ticketDto.setTicketHistories(convertToDto(ticketHistories, toLoad.get(Loads.TicketHistories)));
		
			}
			if (toLoad.has(Loads.AssignedDevelopers)) {
				List<Developer> assignedDevelopers = ticketService.getAllDevelopersForTicket(t.getId());
				ticketDto.setAssignedDevelopers(convertToDto(assignedDevelopers, toLoad.get(Loads.AssignedDevelopers)));
		
			}
			if (toLoad.has(Loads.Comments)) {
				List<Comment> comments = ticketService.getAllCommentForTicket(t.getId());
				ticketDto.setComments(convertToDto(comments, toLoad.get(Loads.Comments)));
			}
			if (toLoad.has(Loads.Project)) {
				Project project = projectService.getProjectForTicket(t.getId());	
				ticketDto.setProject(convertToDto(project, toLoad.get(Loads.Project)));
			}
			if (toLoad.has(Loads.Submitter)) {
				UserAccount user = ticketService.getSubmitterForTicket(t.getId());	
				ticketDto.setSubmitter(convertToDto(user, toLoad.get(Loads.Submitter)));
			}
		}
		return ticketDto;
	}
	
	public TicketHistoryDto convertToDto(TicketHistory t) {
		return convertToDto(t, null);
	}
	
	public TicketHistoryDto convertToDto(TicketHistory t, DtoConverterToLoad toLoad) {
		if (t == null) {
			return null;
		}
		TicketHistoryDto ticketHistoryDto = new TicketHistoryDto(t.getId(), t.getPropertyChanged(), t.getOldValueOfProperty(),
				t.getNewValueOfProperty(), t.getDateOfChange(),null);		
				
		if (toLoad != null) {
			if (toLoad.has(Loads.Ticket)) {
				
				Ticket ticket = ticketService.getTicketForTicketHistory(t.getId());			
				ticketHistoryDto.setTicket(convertToDto(ticket, toLoad.get(Loads.Ticket)));
			}
		}
		return ticketHistoryDto;
	}

	

	public UserDto convertToDto(UserAccount a) {
		return convertToDto(a, null);
	}

	public UserDto convertToDto(UserAccount a, DtoConverterToLoad toLoad) {
		if (a == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setUsername(a.getUsername());
		userDto.setEmail(a.getEmail());
		userDto.setPassword(a.getPassword());
		userDto.setFirstName(a.getFirstName());
		userDto.setLastName(a.getLastName());

		if (toLoad != null) {
			if (toLoad.has(Loads.Comments)) {
				List<Comment> comments = ticketService.getAllCommentForUser(a.getUsername());
				userDto.setComments(convertToDto(comments, toLoad.get(Loads.Comments)));
			}
			if (toLoad.has(Loads.CreatedTickets)) {
				List<Ticket> tickets = ticketService.getAllTicketsForSubmitterWithUsername(a.getUsername());
				userDto.setCreatedTickets(convertToDto(tickets, toLoad.get(Loads.CreatedTickets)));
		
			}if (toLoad.has(Loads.TicketAttachments)) {
				List<TicketAttachment> ticketAttachments = ticketService.getAllTicketAttachmentForUser(a.getUsername());
				userDto.setTicketAttachments(convertToDto(ticketAttachments, toLoad.get(Loads.TicketAttachments)));
			}
		}
		return userDto;
	}
	
    public <D, T> List<D> convertToDto(List<T> os) {
        return convertToDto(os, null);
    }

    // for a list or set, each element is converted to a Dto and a list of Dtos is returned
    public <D, T> List<D> convertToDto(List<T> os, DtoConverterToLoad toLoad) {
        List<D> dtos = new ArrayList<>();
        if (os == null) return null;
        for (T o : os) {
            dtos.add(convertToDto(o, toLoad));
        }
        return dtos;
    }

    public <D, T> List<D> convertToDto(Set<T> os) {
        return convertToDto(os, null);
    }

    public <D, T> List<D> convertToDto(Set<T> os, DtoConverterToLoad toLoad) {
        List<D> dtos = new ArrayList<>();
        if (os == null) return null;
        for (T o : os) {
            dtos.add(convertToDto(o, toLoad));
        }
        return dtos;
    }

    // this generic conversion is necessary for converting arrays to Dto lists: here, each element
    // is converted to an appropriately typed Dto.
    @SuppressWarnings("unchecked")
    public <D, T> D convertToDto(T o, DtoConverterToLoad toLoad) {
        if (o instanceof Admin) return (D) convertToDto((Admin) o, toLoad);
        if (o instanceof Comment) return (D) convertToDto((Comment) o, toLoad);
        if (o instanceof Developer) return (D) convertToDto((Developer) o, toLoad);
        if (o instanceof Project) return (D) convertToDto((Project) o, toLoad);
        if (o instanceof ProjectManager) return (D) convertToDto((ProjectManager) o, toLoad);
        if (o instanceof Ticket) return (D) convertToDto((Ticket) o, toLoad);
        if (o instanceof TicketAttachment) return (D) convertToDto((TicketAttachment) o, toLoad);
        if (o instanceof TicketHistory) return (D) convertToDto((TicketHistory) o, toLoad);
        if (o instanceof UserAccount) return (D) convertToDto((UserAccount) o, toLoad);
        throw new IllegalArgumentException("Unrecognized repository object");
    }

}
