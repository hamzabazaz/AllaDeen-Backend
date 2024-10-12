package com.bussiness.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bussiness.jwt.entity.JwtRequest;
import com.bussiness.jwt.entity.JwtResponse;
import com.bussiness.jwt.service.JwtService;



@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private JwtService jwtService;
	
	@PostMapping({"/authenticate"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest)throws Exception {
		return jwtService.createJwtToken(jwtRequest);
	}
}
