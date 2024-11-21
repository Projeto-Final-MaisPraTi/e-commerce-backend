package com.ecommerce.app.service.user;

import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.user.UserRepository;

import com.ecommerce.app.utils.UserContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO getAuthenticatedUserProfile() throws AccountNotFoundException {
        Optional<Integer> userIdOpt = UserContextUtils.getAuthenticatedUserId();
        if (userIdOpt.isEmpty()) {
            throw new RuntimeException("Usuário não autenticado");  // Lança erro caso não esteja autenticado
        }

        Integer userId = userIdOpt.get();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException("Usuário não encontrado"));

        return convertToDTO(user);
    }

    public User createUser(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email já registrado!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(registerRequest.getTypeRole());

        // Assuming Address, Sales, and ItemCart are not being set on registration.
        // If required, set them here.

        return userRepository.save(user);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
            user.setUsername(userDTO.getUsername());
            }
            if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
//            user.setRoles(userDTO.getRoles());
            userRepository.save(user);

            return convertToDTO(user);
        }

        return null;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

    public User getUserWithPermissions(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado!"));
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getPassword()
        );
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado!"));
    }
}