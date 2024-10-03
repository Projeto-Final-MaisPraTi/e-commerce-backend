package com.ecommerce.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nota", nullable = false)
    private int nota;

    @Column(name = "preco", nullable = false)
    private double preco;

    @Column(name = "cor", nullable = false)
    private String cor;

}
