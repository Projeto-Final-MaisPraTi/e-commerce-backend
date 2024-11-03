package com.ecommerce.app.service.user;

import com.ecommerce.app.dto.user.RegisterRequest;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        user.setAddress(user.getAddress());
//        user.setSales(user.getSales());
//        user.setItemCart(user.getItemCart());
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Integer id, User userData) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userData.getUsername());
            user.setEmail(userData.getEmail());
            user.setPassword(passwordEncoder.encode(userData.getPassword()));
//            user.setAddress(userData.getAddress());
//            user.setSales(userData.getSales());
//            user.setItemCart(userData.getItemCart());

            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
