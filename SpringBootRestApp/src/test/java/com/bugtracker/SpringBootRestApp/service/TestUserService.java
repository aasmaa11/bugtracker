package com.bugtracker.SpringBootRestApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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

import com.bugtracker.SpringBootRestApp.dao.AdminRepository;
import com.bugtracker.SpringBootRestApp.dao.DeveloperRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectManagerRepository;
import com.bugtracker.SpringBootRestApp.dao.ProjectRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.model.Admin;
import com.bugtracker.SpringBootRestApp.model.Developer;
import com.bugtracker.SpringBootRestApp.model.Project;
import com.bugtracker.SpringBootRestApp.model.ProjectManager;
import com.bugtracker.SpringBootRestApp.model.UserAccount;
import com.bugtracker.SpringBootRestApp.service.UserService.UserRole;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
	@Mock private ProjectRepository projectDao;
	@Mock private ProjectManagerRepository projectManagerDao;
	@Mock private DeveloperRepository developerDao;
	@Mock private AdminRepository adminDao;
	@Mock private UserAccountRepository userAccountDao;
	
    @InjectMocks private ProjectService projectService;
    @InjectMocks private UserService userService;
    
    private static final String PROJECT_MANAGER_KEY = "TestProjectManager";
    private static final String DEVELOPER_KEY = "TestDeveloper";
    private static final String ADMIN_KEY = "TestAdmin";
    private static final String EXISTING_ADMIN_KEY = "ExistingAdmin";
    private static final int PROJECT_KEY = 123;
    private static final int PROJECT_KEY_2 = 124;
    private static final String PROJECT_NAME = "name 1";
    private static final String PROJECT_DESCRIPTION = "description 1";
    
    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(userAccountDao.findByUsername(anyString()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            if (invocation.getArgument(0).equals(PROJECT_MANAGER_KEY)) {
                                UserAccount projectManager = new ProjectManager();
                                projectManager.setUsername(PROJECT_MANAGER_KEY);
                                projectManager.setFirstName("Joe");
                                projectManager.setLastName("Jones");
                                projectManager.setEmail("pm@email.com");
                                projectManager.setPassword("1111");
                                return projectManager;
                            } else if (invocation.getArgument(0).equals(DEVELOPER_KEY)) {
                            	UserAccount developer = new Developer();
                                developer.setUsername(DEVELOPER_KEY);
                                developer.setFirstName("Joe");
                                developer.setLastName("Jones");
                                developer.setEmail("pm@email.com");
                                developer.setPassword("1111");
                                return developer;
                            } else if (invocation.getArgument(0).equals(ADMIN_KEY)) {
                            	UserAccount admin = new Admin();
                            	admin.setUsername(ADMIN_KEY);
                            	admin.setFirstName("Joe");
                            	admin.setLastName("Jones");
                            	admin.setEmail("pm@email.com");
                            	admin.setPassword("1111");
                                return admin;
                            } else if (invocation.getArgument(0).equals(EXISTING_ADMIN_KEY)) {
                            	UserAccount admin = new Admin();
                            	admin.setUsername(EXISTING_ADMIN_KEY);
                            	admin.setFirstName("Joe");
                            	admin.setLastName("Jones");
                            	admin.setEmail("pm@email.com");
                            	admin.setPassword("1111");
                                return admin;
                            } else {
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
                    } else {
                        return null;
                    }
                });
        lenient()
        .when(adminDao.findByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(ADMIN_KEY) ||
                    		invocation.getArgument(0).equals(EXISTING_ADMIN_KEY)) {
                        Admin admin = new Admin();
                        admin.setUsername(ADMIN_KEY);
                        admin.setFirstName("Joe");
                        admin.setLastName("Jones");
                        admin.setEmail("pm@email.com");
                        admin.setPassword("1111");
                        return admin;
                    } else {
                        return null;
                    }
                });
        lenient()
        .when(userAccountDao.findAll())
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    Set<UserAccount> users = new HashSet<>();
                    UserAccount projectManager = new ProjectManager();
                    projectManager.setUsername(PROJECT_MANAGER_KEY);
                    projectManager.setFirstName("Joe");
                    projectManager.setLastName("Jones");
                    projectManager.setEmail("pm@email.com");
                    projectManager.setPassword("1111");
                    users.add(projectManager);
                    return users;
                });
        lenient()
        .when(projectManagerDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(PROJECT_MANAGER_KEY);
                });
        lenient()
        .when(adminDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(ADMIN_KEY) ||
                    		invocation.getArgument(0).equals(EXISTING_ADMIN_KEY);
                });
        
        lenient()
        .when(userAccountDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                	if (invocation.getArgument(0).equals(DEVELOPER_KEY) ||
                			invocation.getArgument(0).equals(PROJECT_MANAGER_KEY) ||
                			invocation.getArgument(0).equals(ADMIN_KEY)) {
                		return false;
                	}else if(invocation.getArgument(0).equals(EXISTING_ADMIN_KEY)) {
                		return true;
                	}else {
                		return false;
                	}
                	
                });
        lenient()
        .when(developerDao.existsByUsername(anyString()))
        .thenAnswer(
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0).equals(DEVELOPER_KEY);
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
        .when(adminDao.save(any(Admin.class)))
        .thenAnswer(returnParameterAsAnswer);
        lenient()
        .when(userAccountDao.save(any(UserAccount.class)))
        .thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testCreateAdmin() {
        // Create admin instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        Admin admin = null;
        try {
            admin = userService.createAdmin(ADMIN_KEY, email, password, firstName, lastName);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(admin);
        assertEquals(ADMIN_KEY, admin.getUsername());
        assertEquals(email, admin.getEmail());
        assertNotNull(password, admin.getPassword());
        assertEquals(firstName, admin.getFirstName());
        assertEquals(lastName, admin.getLastName());
        
    }
    
    @Test
    public void testCreateProjectManager() {
        // Create admin instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        ProjectManager projectManager = null;
        try {
        	projectManager = userService.createProjectManager(PROJECT_MANAGER_KEY, email, password, firstName, lastName);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(projectManager);
        assertEquals(PROJECT_MANAGER_KEY, projectManager.getUsername());
        assertEquals(email, projectManager.getEmail());
        assertNotNull(password, projectManager.getPassword());
        assertEquals(firstName, projectManager.getFirstName());
        assertEquals(lastName, projectManager.getLastName());
        
    }
    
    @Test
    public void testCreateDeveloper() {
        // Create admin instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        Developer developer = null;
        try {
        	developer = userService.createDeveloper(DEVELOPER_KEY, email, password, firstName, lastName);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(developer);
        assertEquals(DEVELOPER_KEY, developer.getUsername());
        assertEquals(email, developer.getEmail());
        assertNotNull(password, developer.getPassword());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());
        
    }
    
    @Test
    public void testGetUserRole() {
    	UserRole userRole = null;
        try {
        	userRole = userService.getUserRole(EXISTING_ADMIN_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(userRole);
        assertEquals(UserRole.Admin, userRole);
    }
    
    @Test
    public void testChangeUserRole() {
    	UserAccount user = null;
    	try {
    		user = userService.changeUserRole(ADMIN_KEY, UserRole.ProjectManager);
    	} catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(user);
        assertTrue(user instanceof ProjectManager);
    }
    
    @Test
    public void testDeleteAdmin() {
    	Admin admin = null;
        try {
            admin = userService.deleteAdmin(ADMIN_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(admin);
    }
    
    @Test
    public void testDeleteProjectManager() {
    	ProjectManager projectManager = null;
        try {
        	projectManager = userService.deleteProjectManager(PROJECT_MANAGER_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(projectManager);
    }
    
    @Test
    public void testDeleteDeveloper() {
    	Developer developer = null;
        try {
        	developer = userService.deleteDeveloper(DEVELOPER_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(developer);
    }
    
    @Test
    public void testModifyUser() {
    	UserAccount user = null;
		String email = "dev@email.com";
		String password = "120";
		String firstName = "Jake";
		String lastName = "Jake";
    	try {
    		user = userService.modifyUser(EXISTING_ADMIN_KEY, null, email, password, firstName, lastName);
    		
    	} catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(user);
        assertEquals(EXISTING_ADMIN_KEY, user.getUsername());
        assertEquals(email, user.getEmail());
        assertNotNull(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }
    
    @Test
    public void testGetAdmin() {
        // Create admin instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        Admin admin = null;
        try {
            admin = userService.getAdmin(ADMIN_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(admin);
        assertEquals(ADMIN_KEY, admin.getUsername());
        assertEquals(email, admin.getEmail());
        assertNotNull(password, admin.getPassword());
        assertEquals(firstName, admin.getFirstName());
        assertEquals(lastName, admin.getLastName());
    }
    
    
    @Test
    public void testGetProjectManager() {
        // Create admin instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        ProjectManager projectManager = null;
        try {
        	projectManager = userService.getProjectManager(PROJECT_MANAGER_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(projectManager);
        assertEquals(PROJECT_MANAGER_KEY, projectManager.getUsername());
        assertEquals(email, projectManager.getEmail());
        assertNotNull(password, projectManager.getPassword());
        assertEquals(firstName, projectManager.getFirstName());
        assertEquals(lastName, projectManager.getLastName());
        
    }
    
    @Test
    public void testGetDeveloper() {
        // Create admin instance
		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
		
        Developer developer = null;
        try {
        	developer = userService.getDeveloper(DEVELOPER_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertNotNull(developer);
        assertEquals(DEVELOPER_KEY, developer.getUsername());
        assertEquals(email, developer.getEmail());
        assertNotNull(password, developer.getPassword());
        assertEquals(firstName, developer.getFirstName());
        assertEquals(lastName, developer.getLastName());
        
    }
    
    @Test
    public void testGetAllUsers() {
        List<UserAccount> users = userService.getAllUsers();
        assertEquals(1, users.size());
        UserAccount user = users.get(0);

		String email = "pm@email.com";
		String password = "1111";
		String firstName = "Joe";
		String lastName = "Jones";
        assertEquals(PROJECT_MANAGER_KEY, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }
    
}
