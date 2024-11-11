package com.ecommerce.app.dto.user;

import com.ecommerce.app.infra.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeRole", nullable = false)
    private Role typeRole; // "CLIENT", "ADMIN"
}