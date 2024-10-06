package com.ecommerce.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    private String descricao;

    private Integer estoque;

    private String categoria;

    @Column(name = "nota", nullable = false)
    private int nota;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @Column(name = "cor", nullable = false)
    private String cor;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ImageProduct> images = new ArrayList<>();

    public void addImages(List<String> urls) {
        if (urls == null)
            return;
        for (String url : urls) {
            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setImagem(url);
            imageProduct.setProduct(this);
            images.add(imageProduct);
        }
    }
}
