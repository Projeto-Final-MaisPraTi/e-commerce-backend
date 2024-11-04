package com.ecommerce.app.model.grupo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupname", nullable = false)
    private String groupname; // define o nome da role que seria um tipo de grupo, perfil de usuário

}
