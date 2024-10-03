package com.ecommerce.app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
}
