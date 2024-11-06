package com.ecommerce.app.controller.auth;

import com.ecommerce.app.dto.user.LoginRequest;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.user.UserRepository;
import com.ecommerce.app.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService, UserService userService, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.userRepository = userRepository;
    }
	
	@PostMapping("/login")
	public String Login(@RequestBody LoginRequest loginRequest) {
		try {
			String username = loginRequest.getEmail();
			String password = loginRequest.getPassword();
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			return jwtTokenProvider.generateToken(userDetails);
		}catch(AuthenticationException error) {
			throw new RuntimeException("Invalid Credentials");
		}
	}

	@PostMapping("/register")
	public String register(@RequestBody @Valid UserDTO userDTO, @RequestParam List<String> roles){
		try{
			Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

			if(existingUser.isPresent()){
				return "Usuário já existe!";
			}
			userService.createUser(userDTO, roles);
			return "Usuário registrado com sucesso!";
		}catch (Exception e){
			throw new RuntimeException("Falha ao tentar registrar usuário: " + e.getMessage());
		}
	}

	@PostMapping("/logout")
	public String logout(){
		return "Para logar na sua conta novamente insira seus dados!";
	}

//	@Controller
//	public class LoginController {
//		@GetMapping("/login")
//		public String login() {
//			return "login";  // Nome do arquivo HTML na pasta templates (Thymeleaf, por exemplo)
//		}
//	}
	
}
