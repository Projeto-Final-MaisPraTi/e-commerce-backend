package com.ecommerce.app.infra.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication {

    private final String username;
    private final List<String> permissions;

    public CustomAuthentication(String username, List<String> permissions) {
        if(username == null || permissions == null){
            throw new IllegalArgumentException("Nome de usuário e permissões não podem ser nulos!");
        }

        this.username = username;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Credenciais no token JWT (não utilizados)
    @Override
    public Object getCredentials() {
        return null;
    }

    // Detalhes adicionais (não utilizados)
    @Override
    public Object getDetails() {
        return null;
    }

    // O principal é o nome de usuário
    @Override
    public Object getPrincipal() {
        return this.username;
    }

    // A autenticação está sempre verdadeira no caso de um token válido
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
