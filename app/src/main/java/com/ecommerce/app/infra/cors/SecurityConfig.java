package com.ecommerce.app.infra.cors;

import com.ecommerce.app.infra.enums.Roles;
import com.ecommerce.app.infra.security.CustomAuthentication;
import com.ecommerce.app.infra.security.JwtTokenProvider;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.service.user.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.app.infra.security.JwtAuthenticationFilter;
import com.ecommerce.app.service.customUserDetails.CustomUserDetailsService;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
//	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

//	@Autowired
//	private CustomAuthentication customAuthentication;

	@Autowired
	private UserService userService;
//
//	@Autowired
//	private User user;

//	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService customUserDetailsService) {
//		this.jwtAuthFilter = jwtAuthFilter;
//		this.customUserDetailsService = customUserDetailsService;
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http) throws Exception{
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/auth/**").permitAll() // Permitir acesso a rotas de login, registro, etc.
				.requestMatchers("/admin/**").hasRole("ADMIN")
//				.requestMatchers("/user/**").hasAnyRole("CLIENT", "ADMIN")
//				.requestMatchers("/auth/**").hasAnyRole("CLIENT", "ADMIN")
				.requestMatchers("/api/product/**").hasRole("ADMIN")
				.requestMatchers("/api/sales/**").permitAll()
				.anyRequest().authenticated()
			)
//			.httpBasic(Customizer.withDefaults())
//			.formLogin(Customizer.withDefaults())
//			.authenticationProvider(authenticationProvider())
//			.logout(Customizer.withDefaults())
//			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService(){
//		UserDetails commonUser = User.builder()
//				.username("user")
//				.password(passwordEncoder().encode("123"))
//				.roles("CLIENT")
//				.build();
//
//		UserDetails adminUser = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("CLIENT", "ADMIN")
//				.build();
//
//		return  new InMemoryUserDetailsManager(commonUser, adminUser);
//	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter(){
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("role");
		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}

	@Bean
	public static CustomAuthentication customAuthentication(
			JwtTokenProvider jwtTokenProvider,
			UserService userService,
			HttpServletRequest httpServletRequest) {

		String token = httpServletRequest.getHeader("Authorization");
		var cond = token != null  && token.startsWith("Bearer ");

		if(cond){
			token = token.substring(7);
		}else{
			throw new JwtException("Token JWT não encontrado ou expirado!");
		}

		String email = jwtTokenProvider.extractUsername(token);

		User user = userService.findByEmail(email);

		if(user == null){
			throw new UsernameNotFoundException("Email não encontrado: " + email);
		}

//		List<Roles> roles = List.of(user.getRoles());
		List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()));

		return new CustomAuthentication(user.getUsername(), roles);
	}


}
