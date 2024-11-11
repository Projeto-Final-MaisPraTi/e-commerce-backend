package com.ecommerce.app.infra.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;

public class CustomAuthentication implements Authentication {

    private final String username;
    private final List<GrantedAuthority> permissions;

    public CustomAuthentication(String username, List<GrantedAuthority> permissions) {
        if (username == null || permissions == null) {
            throw new IllegalArgumentException("Nome de usuário e permissões não podem ser nulos!");
        }
        this.username = username;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.permissions;
    }

    public void setDetails(Object obj) {
        throw new IllegalArgumentException("Já está autenticado");
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Já está autenticado");
    }

    @Override
    public String getName() {
        return this.username;
    }
}