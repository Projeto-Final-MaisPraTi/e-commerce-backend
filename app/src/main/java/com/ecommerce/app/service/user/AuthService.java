package com.ecommerce.app.service.user;

//import com.ecommerce.app.model.role.Role;
//import com.ecommerce.app.repository.role.RoleRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
//    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
//        this.roleRepository = roleRepository;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalStateException("Credenciais inválidas.");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Role.ADMIN);

        userRepository.save(user);

        String token = tokenProvider.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = tokenProvider.generateToken(user);
        return new AuthResponse(token);
    }
}
