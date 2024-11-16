package com.ecommerce.app.controller.auth;

import com.ecommerce.app.dto.user.AuthResponse;
import com.ecommerce.app.dto.user.LoginRequest;
import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.infra.security.JwtTokenProvider;
import com.ecommerce.app.service.user.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
		this.authService = authService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
		try {
			AuthResponse response = authService.register(registerRequest);
			return ResponseEntity.ok(response);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
		try {
			AuthResponse response = authService.login(loginRequest);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno.");
		}
	}

	@GetMapping("/decode")
	public ResponseEntity<?> decodeToken(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Token não fornecido ou inválido.");
		}

		String token = authHeader.substring(7);
		try {
			Map<String, Object> decodedClaims = jwtTokenProvider.extractAllClaims(token);
			return ResponseEntity.ok(decodedClaims);
		} catch (ExpiredJwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado.");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido.");
		}
	}
}