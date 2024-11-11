package com.ecommerce.app.controller.auth;

import com.ecommerce.app.dto.user.AuthResponse;
import com.ecommerce.app.dto.user.LoginRequest;
import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.user.UserRepository;
import com.ecommerce.app.service.user.AuthService;
import com.ecommerce.app.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.infra.security.JwtTokenProvider;
import com.ecommerce.app.service.customUserDetails.CustomUserDetailsService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final AuthService authService;

	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService, UserService userService, UserRepository userRepository, AuthService authService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.authService = authService;
    }

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			String username = loginRequest.getEmail();
			String password = loginRequest.getPassword();

			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String token = jwtTokenProvider.generateToken(userDetails);
			return ResponseEntity.ok(new AuthResponse(token));
		} catch(AuthenticationException error) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
		Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
		if (existingUser.isPresent()) {
			return ResponseEntity.badRequest().body("Usuário já existe!");
		}

		try {
			AuthResponse response = authService.register(registerRequest);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao tentar registrar usuário: " + e.getMessage());
		}
	}

	@PostMapping("/logout")
	public String logout(){
		return "Para logar na sua conta novamente insira seus dados!";
	}
}
