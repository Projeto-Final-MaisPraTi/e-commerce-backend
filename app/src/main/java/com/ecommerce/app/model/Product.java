package com.ecommerce.app.model;

import com.ecommerce.app.dto.produtos.ProductUpdateDTO;
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

    @Column(columnDefinition = "TEXT")
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
            return ;
        for (String url : urls) {
            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setImagem(url);
            imageProduct.setProduct(this);
            imageProduct.setCapaProduto(false);
            images.add(imageProduct);
        }
    }

    public void update(ProductUpdateDTO productDTO) {
        if (productDTO.nome() != null) {
            this.nome = productDTO.nome();
        }
        if (productDTO.descricao() != null) {
            this.descricao = productDTO.descricao();
        }
        if (productDTO.estoque() != null) {
            this.estoque = productDTO.estoque();
        }
        if (productDTO.categoria() != null) {
            this.categoria = productDTO.categoria();
        }
        if (productDTO.preco() != null) {
            this.preco = productDTO.preco();
        }
        if (productDTO.cor() != null) {
            this.cor = productDTO.cor();
        }
        if (productDTO.cover() != null) {
            ImageProduct newCover = new ImageProduct();
            newCover.setCapaProduto(true);
            newCover.setProduct(this);
            newCover.setImagem(productDTO.cover());
            images.add(newCover);
        }
        if (productDTO.images() != null) {
            if (!productDTO.images().isEmpty()){
                productDTO.images().stream().forEach(img ->
                        images.add(new ImageProduct(img, this, false))
                );
            }
        }
    }
}
