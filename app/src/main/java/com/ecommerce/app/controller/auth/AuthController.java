package com.ecommerce.app.controller.auth;

import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.login.LoginDTO;
import com.ecommerce.app.infra.security.JwtTokenProvider;
import com.ecommerce.app.service.customUserDetails.CustomUserDetailsService;

import jakarta.validation.Valid;

import java.util.List;

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

	@PostMapping("/register")
	public String register(@RequestBody @Valid UserDTO userDTO, List<String> groups){
		try{
			userService.createUser(userDTO, groups);
			return "Usuário registrado com sucesso!";
		}catch (Exception e){
			throw new RuntimeException("Falha ao tentar registrar usuário: " + e.getMessage());
		}
	}

	@PostMapping("/logout")
	public String logout(){
		return "Paraa logar na sua conta novamente insira seus dados!";
	}

//	@Controller
//	public class LoginController {
//		@GetMapping("/login")
//		public String login() {
//			return "login";  // Nome do arquivo HTML na pasta templates (Thymeleaf, por exemplo)
//		}
//	}
	
}
