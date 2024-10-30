package com.ecommerce.app.model.product;

import java.util.List;

import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.model.reviews.Reviews;
import com.ecommerce.app.model.salesItems.SalesItems;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nota", nullable = false)
    private int nota;

    @Column(name = "preco", nullable = false)
    private double preco;

    @Column(name = "cor", nullable = false)
    private String cor;
    
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;
    
    @Column(name = "estoque", nullable = false)
    private int estoque;
    
    @Column(name = "categoria", nullable = false)
    private String categoria;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImages> imagens;
    
    @OneToMany(mappedBy = "product")
    private List<SalesItems> itensVenda;
    
    @OneToMany(mappedBy = "product")
    private List<Reviews> reviews;

}
