package com.ecommerce.app.dto.user;

import com.ecommerce.app.infra.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

// RegisterRequest.java

@Data
public class RegisterRequest {
    private String username;
    private String email;
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeRole", nullable = false)
    private Roles typeRole; // "CLIENT", "ADMIN"
}