package com.bugtracker.SpringBootRestApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.SpringBootRestApp.service.ProjectService;
import com.bugtracker.SpringBootRestApp.service.TicketService;
import com.bugtracker.SpringBootRestApp.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
    @Autowired private ProjectService projectService;
    @Autowired private TicketService ticketService;
    @Autowired private UserService userService;
    
    @Autowired private DtoConverterRestController converter;

}
