package com.ecommerce.app.dto.user;

import com.ecommerce.app.infra.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotNull(message = "Nome de usuário não pode ser nulo")
    private String username;

    @NotNull(message = "Email não pode ser nulo")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeRole", nullable = false)
    private Role typeRole; // "CLIENT", "ADMIN"
}