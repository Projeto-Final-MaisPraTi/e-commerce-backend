package com.ecommerce.app.model.role;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename", nullable = false)
    private String rolename; // define o nome da role que seria um tipo de grupo, perfil de usuário

}
