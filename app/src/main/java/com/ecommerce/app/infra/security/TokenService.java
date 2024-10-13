package com.ecommerce.app.infra.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import com.ecommerce.app.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private Key getSigningKey() {
        // Gera uma chave segura de 256 bits
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(User user) {
        try {
            Key signingKey = getSigningKey();
            return Jwts.builder()
                    .setIssuer("e-commerce-backend") // Definir o emissor do token
                    .setSubject(user.getEmail())  // Definir o "subject", o email do usuário
                    .setExpiration(generateExpirationDate()) // Data de expiração do token
                    .setIssuedAt(new Date()) // Data de criação do token
                    .signWith(signingKey, SignatureAlgorithm.HS256) // Assinar com o algoritmo HS256
                    .compact(); // Gera o token
        } catch (JwtException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Definir a chave de assinatura para validação
                    .setAllowedClockSkewSeconds(60) // Permitir uma diferença de 60 segundos para expiração
                    .requireIssuer("e-commerce-backend") // Verificar o emissor do token
                    .build()
                    .parseClaimsJws(token) // Valida o token e extrai as claims
                    .getBody(); // Extrai as informações do corpo do JWT

            return claims.getSubject(); // Retorna o "subject" (email do usuário)
        } catch (JwtException e) {
            return null; // Token inválido
        }
    }

    private Date generateExpirationDate() {
        // Data de expiração: 2 horas a partir do momento atual
        return Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
    }
}
