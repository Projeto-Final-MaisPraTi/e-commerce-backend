package com.ecommerce.app.infra.security;

import com.ecommerce.app.repository.user.UserRepository;
import com.ecommerce.app.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationProvider {

	private final UserService userService;
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	private Key secretKey;

	@Value("${jwt.expirationMs}")
	private Long jwtExpirationMs;

	@Value("${jwt.secret}")
	private String secret;

	@PostConstruct
	public void init() {
		if (secret.length() < 32) {
			throw new IllegalArgumentException("Chave secreta JWT deve ter pelo menos 256 bits (32 caracteres)");
		}
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // Garante a segurança com 256 bits
	}

//	public JwtTokenProvider(@Value("${jwt.secret}") String secret, UserService userService) {
//        this.userService = userService;
//        if (secret.length() < 32) {
//			throw new IllegalArgumentException("Chave secreta JWT deve ter pelo menos 256 bits (32 caracteres)");
//		}
//		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // Garante a segurança com 256 bits
//	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject); // Isso retorna o email
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			throw new RuntimeException("Token inválido ou expirado", e);
		}
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}

	public UserDetails getUserWithPermissions(String email){
		return userService.getUserWithPermissions(email);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials(); // Token JWT enviado na requisição

		// Extrair o nome de usuário (email) do token
		String username = extractUsername(token);

		// Carregar o UserDetails (usuário com permissões)
		UserDetails userDetails = getUserWithPermissions(username);

		// Validar o token com o UserDetails
		if (isTokenValid(token, userDetails)) {
			// Se o token for válido, criamos um CustomAuthentication diretamente com o nome de usuário e permissões
//			List<String> permissions = userDetails.getAuthorities().stream()
//					.map(GrantedAuthority::getAuthority)
//					.collect(Collectors.toList());

			// Se o token for válido, retornar o Authentication com o UserDetails
//			return new CustomAuthentication(userDetails.getUsername(), permissions);

			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}

		// Se o token for inválido, lançar exceção
		throw new AuthenticationException("Token inválido ou expirado") {};
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}

	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email não encontrado: " + email));
	}
}
