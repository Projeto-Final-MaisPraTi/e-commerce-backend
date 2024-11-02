package com.ecommerce.app.dto.user;

import lombok.Data;

// RegisterRequest.java

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}