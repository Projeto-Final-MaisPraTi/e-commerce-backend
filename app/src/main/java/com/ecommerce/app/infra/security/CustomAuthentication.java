package com.ecommerce.app.infra.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication {

    private final UserIdentification userIdentification;

    public CustomAuthentication(UserIdentification userIdentification) {

        if(userIdentification == null){
            throw new ExceptionInInitializerError(
                    "Não é possível criar um customAuthentication sem a identificação do usuário!"
            );
        }

        this.userIdentification = userIdentification;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userIdentification
                .getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission))
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userIdentification;
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
        return this.userIdentification.getUsername();
    }
}
