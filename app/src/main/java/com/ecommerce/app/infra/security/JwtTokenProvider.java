package com.ecommerce.app.infra.security;

import com.ecommerce.app.model.user.User;
import com.ecommerce.app.service.user.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final UserService userService;

    @Value("${jwt.expirationMs}")
    private Long jwtExpirationMs;

    @Autowired
    public JwtTokenProvider(UserService userService, @Value("${jwt.secret}") String secret) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("Chave secreta JWT deve ter pelo menos 256 bits (32 caracteres).");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.userService = userService;
    }

    public String generateToken(UserDetails userDetails) {
        User user = userService.findByEmail(((User)userDetails).getEmail());
        Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "role", user.getRoles().name(),
                "email", user.getEmail()
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expirado", e);
        } catch (JwtException e) {
            throw new JwtException("Token inválido", e);
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}