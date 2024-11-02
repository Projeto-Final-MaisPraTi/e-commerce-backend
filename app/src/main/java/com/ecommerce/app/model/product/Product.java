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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String nome;

    @Column(name = "score", nullable = false)
    private int nota;

    @Column(name = "price", nullable = false)
    private double preco;

    @Column(name = "color", nullable = false)
    private String cor;
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String descricao;
    
    @Column(name = "stock", nullable = false)
    private int estoque;
    
    @Column(name = "category", nullable = false)
    private String categoria;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImages> imagens;
    
    @OneToMany(mappedBy = "product")
    private List<SalesItems> itensVenda;
    
    @OneToMany(mappedBy = "product")
    private List<Reviews> reviews;

}
