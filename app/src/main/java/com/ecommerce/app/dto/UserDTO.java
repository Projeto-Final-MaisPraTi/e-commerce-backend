package com.ecommerce.app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;

    private String nome; // Pode incluir restrições de tamanho como @Size(max = 100)

    private String email; // Pode incluir validação para formato de e-mail

    private String senha; // Idealmente, não expor em DTOs para evitar vazamentos

    private String telefone; // Pode incluir restrições de formato
}