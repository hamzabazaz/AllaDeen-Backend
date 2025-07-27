package com.bussiness.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bussiness.jwt.entity.User;
import com.bussiness.jwt.service.UserService;

import jakarta.annotation.PostConstruct;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
public class UserController {
	@Autowired
	private UserService userService; 
	
	@PostConstruct
	public void initRolesAndUsers() {
		userService.initRolesAndUser();
	}

	
	
	@PostMapping({"/registerNewUser"})
	public User registerNewUser(@RequestBody User user) {
		
		return userService.registerNewUser(user);
		
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "This URL is only accessible to admin";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "This URL is only accessible to the user";
	}

}
