package com.gil.couponsys02.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.LoginCacheManager;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.models.AuthenticationRequest;
import com.gil.couponsys02.models.AuthenticationResponse;
import com.gil.couponsys02.services.ClientService;
import com.gil.couponsys02.services.CompanyService;
import com.gil.couponsys02.utils.JwtUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginManager loginManager;
	
	@Autowired
	private LoginCacheManager loginCacheManager;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping
	public ResponseEntity<?> createAuthenticateionToken(@RequestBody AuthenticationRequest authenticationRequest) throws UnauthorizedAccessException {
		
		ClientService clientService = loginManager.login(authenticationRequest.getUsername(), authenticationRequest.getPassword(), authenticationRequest.getClientType());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String jwt = jwtTokenUtil.generateToken(clientService, authenticationRequest.getClientType(), userDetails);
		loginCacheManager.insertService(jwtTokenUtil.extractClientType(jwt).name() + jwtTokenUtil.extractClientServiceId(jwt), clientService);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
}
