package com.ecommerce.app.infra.security;

import com.ecommerce.app.infra.enums.Role;
import com.ecommerce.app.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Integer id;

    private String email;

    private String password;

    private Role roles;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.roles.name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        Collection<GrantedAuthority> authorities = List.of(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
