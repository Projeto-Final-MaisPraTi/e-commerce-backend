package com.ecommerce.app.utils;

import com.ecommerce.app.infra.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserContextUtils {

    /**
     * Retorna o ID do usuário autenticado.
     * Caso o usuário não esteja autenticado, retorna um Optional vazio.
     *
     * @return um Optional contendo o ID do usuário, se autenticado, ou um Optional vazio.
     */
    public static Optional<Integer> getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails) {
                return Optional.of(((CustomUserDetails) principal).getId());
            }
        }

        return Optional.empty();  // Retorna um Optional vazio caso o usuário não esteja autenticado
    }
}
