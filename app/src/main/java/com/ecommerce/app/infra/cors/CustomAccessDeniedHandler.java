package com.ecommerce.app.infra.cors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Configura o status HTTP 403 (Forbidden)
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Define o tipo de conteúdo como JSON
        response.setContentType("application/json");

        // Escreve a mensagem de erro no corpo da resposta
        response.getWriter().write("{\"error\": \"Você não tem autorização para acessar este recurso.\"}");
    }
}
