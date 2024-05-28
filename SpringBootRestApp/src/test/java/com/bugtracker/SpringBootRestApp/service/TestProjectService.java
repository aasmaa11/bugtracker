package com.bugtracker.SpringBootRestApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectManagerRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.TicketRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.Ticket;

@ExtendWith(MockitoExtension.class)
public class TestProjectService {
	
	@Mock private ProjectRepository projectDao;
	@Mock private ProjectManagerRepository projectManagerDao;
	@Mock private DeveloperRepository developerDao;
	@Mock private UserAccountRepository userAccountDao;
	@Mock private TicketRepository ticketDao;
	
    @InjectMocks private ProjectService projectService;
    @InjectMocks private UserService userService;
    //@InjectMocks private TicketService ticketService;


    private static final String PROJECT_MANAGER_KEY = "TestProjectManager";
    private static final String PROJECT_MANAGER_KEY_2 = "TestProjectManager_2";
    private static final String DEVELOPER_KEY = "TestDeveloper";
    private static final String DEV3_USERNAME = "TestDeveloper123";
    private static final int TICKET_KEY = 70;
    private static final int PROJECT_KEY = 123;
    private static final int PROJECT_KEY_2 = 124;
    private static final int PROJECT_KEY_10 = 300;
    private static final String PROJECT_NAME = "name 1";
    private static final String PROJECT_DESCRIPTION = "description 1";

    @BeforeEach
    public void setMockOutput() {
        lenient()
        .when(ticketDao.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                    	Ticket ticket = new Ticket();
                        Project project = new Project();
                        project.setName(PROJECT_NAME);
                        project.setDescription(PROJECT_DESCRIPTION);
                        ticket.setProject(project);
                        return ticket;
                    } else {
                        return null;
                    }
                });
        lenient()
                .when(projectManagerDao.findByUsername(anyString()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            if (invocation.getArgument(0).equals(PROJECT_MANAGER_KEY)) {
                                ProjectManager projectManager = new ProjectManager();
                                projectManager.setUsername(PROJECT_MANAGER_KEY);
                                projectManager.setFirstName("Joe");
                                projectManager.setLastName("Jones");
                                projectManager.setEmail("pm@email.com");
                                projectManager.setPassword("1111");
                                return projectManager;
                            } else if (invocation.getArgument(0).equals(PROJECT_MANAGER_KEY_2)) {
                                ProjectManager projectManager = new ProjectManager();
                                projectManager.setUsername(PROJECT_MANAGER_KEY_2);
                                projectManager.setFirstName("Jake");
                                projectManager.setLastName("Joe");
                                projectManager.setEmail("pm2@email.com");
                                projectManager.setPassword("2222");
                                return projectManager;
                            } else {
                                return null;
                            }
                        });
        
        lenient()
        .when(projectManagerDao.findByProjectsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PROJECT_KEY_2)) {
                        ProjectManager projectManager = new ProjectManager();
                        projectManager.setUsername(PROJECT_MANAGER_KEY);
                        return projectManager;
                    } else {
                        return null;
                    }
                });
        
        lenient()
        .when(projectDao.findById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PROJECT_KEY)) {
                        Project project = new Project();
                        project.setName("project 1");
                        project.setDescription("description 1");
                        return project;
                    } else if(invocation.getArgument(0).equals(PROJECT_KEY_10)){
                        Project project = new Project();
                        ProjectManager projectManager = new ProjectManager();
                        projectManager.setUsername(PROJECT_MANAGER_KEY);
                        project.setName("project 1");
                        project.setDescription("description 1");
                        project.setProjectManager(projectManager);
                        return project;
                    }else if(invocation.getArgument(0).equals(PROJECT_KEY_2)) {
                        Developer developer = new Developer();
                        developer.setUsername(DEVELOPER_KEY);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        Project project = new Project();
                        project.setName("project 1");
                        project.setDescription("description 1");
                        Set<Developer> devs = new HashSet<Developer>();
                        devs.add(developer);
                        project.setAssignedDevelopers(devs);
                        return project;
                    	
                    }else {
                        return null;
                    }
                });
        
        lenient()
        .when(projectDao.findByTicketsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TICKET_KEY)) {
                        Project project = new Project();
                        project.setName(PROJECT_NAME);
                        project.setDescription(PROJECT_DESCRIPTION);
                        return project;
                    } else {
                        return null;
                    }
                });
        
        
        lenient()
        .when(projectDao.findAll())
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    Set<Project> projects = new HashSet<>();

                    Project project = new Project();
                    project.setName(PROJECT_NAME);
                    project.setDescription(PROJECT_DESCRIPTION);
                    projects.add(project);
                    return projects;
                });
        
        lenient()
        .when(projectDao.findByAssignedDevelopersUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEV3_USERNAME)) {
                        List<Project> projects = new ArrayList<>();

                        Project project = new Project();
                        project.setName(PROJECT_NAME);
                        project.setDescription(PROJECT_DESCRIPTION);
                        projects.add(project);
                        return projects;
                    	
                    }else {
                        return null;
                    }
                });
        
        lenient()
        .when(projectDao.findByProjectManagerUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PROJECT_MANAGER_KEY)) {
                        List<Project> projects = new ArrayList<>();

                        Project project = new Project();
                        project.setName(PROJECT_NAME);
                        project.setDescription(PROJECT_DESCRIPTION);
                        projects.add(project);
                        return projects;
                    	
                    }else {
                        return null;
                    }
                });

        lenient()
        .when(developerDao.findByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DEVELOPER_KEY)) {
                        Developer developer = new Developer();
                        developer.setUsername(DEVELOPER_KEY);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        return developer;
                    } else {
                        return null;
                    }
                });
        
        lenient()
        .when(developerDao.findByProjectsId(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(PROJECT_KEY)) {
                    	ArrayList<Developer> devArray =  new ArrayList<Developer>();
                        Developer developer = new Developer();
                        developer.setUsername(DEVELOPER_KEY);
                        developer.setFirstName("Joe");
                        developer.setLastName("Jones");
                        developer.setEmail("pm@email.com");
                        developer.setPassword("1111");
                        devArray.add(developer);
                        return devArray;
                    } else {
                        return null;
                    }
                });
        
        lenient()
        .when(projectManagerDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(PROJECT_MANAGER_KEY) ||
                    		invocation.getArgument(0).equals(PROJECT_MANAGER_KEY_2);
                });
        
        lenient()
        .when(userAccountDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return false;
                });
        lenient()
        .when(developerDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(DEVELOPER_KEY);
                });
        lenient()
        .when(projectDao.existsById(anyInt()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(PROJECT_KEY) ||
                    		invocation.getArgument(0).equals(PROJECT_KEY_2);
                });
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };
        lenient()
                .when(projectManagerDao.save(any(ProjectManager.class)))
                .thenAnswer(returnParameterAsAnswer);
        lenient()
                .when(developerDao.save(any(Developer.class)))
                .thenAnswer(returnParameterAsAnswer);
        lenient()
                .when(projectDao.save(any(Project.class)))
                .thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testGetProjectForTicket() {
    	Project project = projectService.getProjectForTicket(TICKET_KEY);
    	assertNotNull(project);
        assertEquals(PROJECT_NAME, project.getName());
        assertEquals(PROJECT_DESCRIPTION, project.getDescription());
    }
    
    @Test
    public void testGetAllProjectsForDeveloper() {
        List<Project> projects = projectService.getAllProjectsForDeveloper(DEV3_USERNAME);
        assertEquals(1, projects.size());
        Project project = projects.get(0);
        assertEquals(PROJECT_NAME, project.getName());
        assertEquals(PROJECT_DESCRIPTION, project.getDescription());
    }
    
    @Test
    public void testGetAllProjectsForProjectManager() {
        List<Project> projects = projectService.getAllProjectsForProjectManager(PROJECT_MANAGER_KEY);
        assertEquals(1, projects.size());
        Project project = projects.get(0);
        assertEquals(PROJECT_NAME, project.getName());
        assertEquals(PROJECT_DESCRIPTION, project.getDescription());
    }
    
    @Test
    public void testCreateProject() {

        // Create project instance
        String name = "project 1";
        String description = "description 1";

        // Test equality
        Project project = null;
        try {
            project = projectService.createProject(name, description, PROJECT_MANAGER_KEY, null);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
        assertNotNull(project.getProjectManager());
        assertEquals(PROJECT_MANAGER_KEY, project.getProjectManager().getUsername());
    }
    
    @Test
    public void testGetProjectManagerOfExistingProject() {
        // Test equality
        ProjectManager projectManager = null;
        try {
            projectManager = projectService.getProjectManagerForProject(PROJECT_KEY_10);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(projectManager);
        assertEquals(PROJECT_MANAGER_KEY, projectManager.getUsername());
    }
    
    @Test
    public void testGetAllAssignedDevelopersForProject() {
        // Create project manager instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        // Test equality
        List<Developer> devs = null;
        try {
            devs = projectService.getAllAssignedDevelopersForProject(PROJECT_KEY_2);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(devs);
        assertEquals(1, devs.size());
        assertEquals(DEVELOPER_KEY, devs.get(0).getUsername());
        assertEquals(email, devs.get(0).getEmail());
        assertEquals(password, devs.get(0).getPassword());
        assertEquals(firstName, devs.get(0).getFirstName());
        assertEquals(lastName, devs.get(0).getLastName());
    }
    
    @Test
    public void testModifyProject() {
        
        String newName = "project new";
        String newDescription = "description new";

        Project project = null;
        try {
            project = projectService.modifyProject(PROJECT_KEY, newName, newDescription);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(project);
        assertEquals(newName, project.getName());
        assertEquals(newDescription, project.getDescription());
        
    }
    
    @Test
    public void testAssignProjectManagerToProject() {
        // Create project manager instance
		String emailPm = "pm2@email.com";
		String passwordPm = "2222";
		String firstNamePm = "Jake";
		String lastNamePm = "Joe";
		

        // Test equality
        Project project = null;
        try {
            project = projectService.assignProjectManagerToProject(PROJECT_KEY, PROJECT_MANAGER_KEY_2);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(project);
        assertEquals(PROJECT_MANAGER_KEY_2, project.getProjectManager().getUsername());
        assertEquals(emailPm, project.getProjectManager().getEmail());
        assertEquals(passwordPm, project.getProjectManager().getPassword());
        assertEquals(firstNamePm, project.getProjectManager().getFirstName());
        assertEquals(lastNamePm, project.getProjectManager().getLastName());
    	
    }

    @Test
    public void testAddDeveloperToProject() {
        // Create project manager instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";


        // Test equality
        Project project = null;
        try {
            project = projectService.addDeveloperToProject(PROJECT_KEY, DEVELOPER_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(project);
        for (Developer d : project.getAssignedDevelopers()) {
            if (d.getUsername().equals(DEVELOPER_KEY)) {
                assertEquals(password, d.getPassword());
                assertEquals(email, d.getEmail());
                assertEquals(firstName, d.getFirstName());
                assertEquals(lastName, d.getLastName());
                break;
            }
        }
    }
    
    @Test
    public void testDeleteProject() {
    	Project project = null;
        try {
            project = projectService.deleteProject(PROJECT_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(project);
    }
    
    @Test
    public void testRemoveDeveloperFromProject() {
    	Project project = null;

        try {
            project = projectService.removeDeveloperFromProject(PROJECT_KEY_2, DEVELOPER_KEY);
        } catch (IllegalArgumentException e) {
        	
            fail();
        }
        assertNotNull(project);
        assertEquals(0, project.getAssignedDevelopers().size());
    }
    
    @Test
    public void testGetAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        assertEquals(1, projects.size());
        Project project = projects.get(0);
        assertEquals(PROJECT_NAME, project.getName());
        assertEquals(PROJECT_DESCRIPTION, project.getDescription());
    }
}
