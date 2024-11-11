package com.ecommerce.app.infra.security;

import com.ecommerce.app.infra.enums.Roles;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.app.service.customUserDetails.CustomUserDetailsService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletRequest;

import jakarta.servlet.ServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter implements DoFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private CustomAuthentication customAuthentication;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

//    private final CustomUserDetailsService customUserDetailsService;

//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService){
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.customUserDetailsService = customUserDetailsService;
//    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String jwt = authHeader.substring(7);
//
//        if (jwt == null || jwt.isEmpty()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String email = jwtTokenProvider.extractUsername(jwt); // Agora isso extrai o email
//        UserDetails userDetails = null;
//
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            userDetails = customUserDetailsService.loadUserByUsername(email); // Aqui usamos email
//        }
//
//        if (jwtTokenProvider.isTokenValid(jwt, userDetails)) {
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }
//
//        filterChain.doFilter(request, response);
//    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String jwt = authHeader.substring(7);
//
//        if (jwt == null || jwt.isEmpty()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String email = jwtTokenProvider.extractUsername(jwt);
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = jwtTokenProvider.loadUserByEmail(email); // Este metodo poderia ser extraído do JwtTokenProvider
//            if (jwtTokenProvider.isTokenValid(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String email = jwtTokenProvider.extractUsername(token);
            User user = userService.findByEmail(email);

            if (user != null) {

                List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))  // Supondo que role seja um enum
                        .collect(Collectors.toList());


                CustomAuthentication authentication = new CustomAuthentication(
                        user.getUsername(), user.getRoles() // Ou como você obtém as permissões do usuário
                );

                var http = new WebAuthenticationDetailsSource().buildDetails(httpRequest);
                authentication.setDetails(http);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response); // Continue o processamento da requisição
    }
}
