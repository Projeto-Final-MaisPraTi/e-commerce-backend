package com.ecommerce.app.infra.security;

import com.ecommerce.app.model.user.User;
import com.ecommerce.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userService.getUserWithPermissions(login);

        if(user != null){
            boolean passwordsMatch = passwordEncoder.matches(password, user.getPassword());

            if(passwordsMatch){
                UserIdentification userIdentification = new UserIdentification(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPermissions()
                );

                return new CustomAuthentication(userIdentification);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
