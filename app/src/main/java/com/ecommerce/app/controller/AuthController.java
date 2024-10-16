package com.ecommerce.app.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.LoginDTO;
import com.ecommerce.app.security.JwtTokenProvider;
import com.ecommerce.app.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@PostMapping("/login")
	public String Login(@RequestBody LoginDTO loginDTO) {
		try {
			String username = loginDTO.getUsername();
			String password = loginDTO.getPassword();
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			return jwtTokenProvider.generateToken(userDetails);
		}catch(AuthenticationException error) {
			throw new RuntimeException("Invalid Credentials");
		}
	}
	
}
