package com.ecommerce.app.service.user;

import com.ecommerce.app.infra.enums.Role;
import com.ecommerce.app.repository.user.UserRepository;
import com.ecommerce.app.dto.user.LoginRequest;
import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.dto.user.AuthResponse;
import com.ecommerce.app.infra.security.JwtTokenProvider;
import com.ecommerce.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalStateException("Email já cadastrado.");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singletonList("CLIENT"));

        userRepository.save(user);

        String token = tokenProvider.generateToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // Verificar se o usuário existe no banco de dados
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = tokenProvider.generateToken(user);
        return new AuthResponse(token);
    }
}