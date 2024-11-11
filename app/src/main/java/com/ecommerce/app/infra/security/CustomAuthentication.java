package com.ecommerce.app.infra.security;

import com.ecommerce.app.infra.enums.Roles;
import jakarta.servlet.Filter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication {

    private final String username;
//    private final List<Roles> permissions;
    private final List<GrantedAuthority> permissions;

//    public CustomAuthentication(String username, List<Roles> permissions) {
    public CustomAuthentication(String username, List<GrantedAuthority> permissions) {
        if(username == null || permissions == null){
            throw new IllegalArgumentException("Nome de usuário e permissões não podem ser nulos!");
        }

        this.username = username;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return permissions.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
//                .collect(Collectors.toList());
        return permissions;
    }

    // Credenciais no token JWT (não utilizados)
    @Override
    public Object getCredentials() {
        return null;
    }

    // Detalhes adicionais (não utilizados)
    @Override
    public Object getDetails() {
        return this.permissions;
    }

    public void setDetails(Object obj) {
        throw new IllegalArgumentException("Já está autenticado");
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
