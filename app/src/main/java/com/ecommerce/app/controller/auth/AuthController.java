package com.ecommerce.app.controller.auth;

import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.service.user.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.user.LoginRequest;
import com.ecommerce.app.infra.security.JwtTokenProvider;
import com.ecommerce.app.service.customUserDetails.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;
	private final UserService userService;

	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
    }
	
	@PostMapping("/login")
	public String Login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
			);
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			return jwtTokenProvider.generateToken(userDetails);
		}catch(AuthenticationException error) {
			throw new RuntimeException("Invalid Credentials");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
		try {
			userService.createUser(registerRequest);
			return ResponseEntity.ok("Usuário registrado com sucesso!");
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao tentar registrar usuário.");
		}
	}

//	@Controller
//	public class LoginController {
//		@GetMapping("/login")
//		public String login() {
//			return "login";  // Nome do arquivo HTML na pasta templates (Thymeleaf, por exemplo)
//		}
//	}
	
}
