package com.ecommerce.app.service.customUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.user.UserRepository;

@Service
//public class CustomUserDetailsService implements UserDetailsService {
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email) // Mudou para buscar por email
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return user; // Retorna a instância de User, que implementa UserDetails
    }
}
