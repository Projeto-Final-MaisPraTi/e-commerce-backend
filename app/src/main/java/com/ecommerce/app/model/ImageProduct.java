package com.ecommerce.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "imagens_produtos")
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imagem;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "id_produto")
    private Product product;

    @Setter
    @Column(name = "capa_produto")
    private Boolean capaProduto;

    public ImageProduct() {};

    public ImageProduct(String imagem, Product product, Boolean capaProduto) {
        this.imagem = imagem;
        this.product = product;
        this.capaProduto = capaProduto;
    }
}
