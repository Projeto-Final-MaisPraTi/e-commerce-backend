package com.ecommerce.app.model.product;

import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.model.reviews.Reviews;
import com.ecommerce.app.model.salesItems.SalesItems;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome do produto não pode estar em branco")
    @Column(name = "name", nullable = false)
    private String nome;

    @PositiveOrZero(message = "A nota deve ser positiva ou zero")
    @Column(name = "score", nullable = false)
    private int nota;

    @NotNull(message = "O preço não pode estar em branco")
    @PositiveOrZero(message = "O preço deve ser positivo")
    @Column(name = "price", nullable = false)
    private Double preco;

    @NotBlank(message = "A cor não pode estar em branco")
    @Column(name = "color", nullable = false)
    private String cor;

    @Column(name = "description", columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "O estoque não pode estar em branco")
    @PositiveOrZero(message = "O estoque deve ser positivo")
    @Column(name = "stock")
    private Integer estoque;

    @NotBlank(message = "A categoria não pode estar em branco")
    @Column(name = "category")
    private String categoria;

    @PositiveOrZero(message = "O desconto deve ser positivo ou zero")
    @Column
    private Integer discount;

    @Column
    private Boolean flashSale;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImages> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<SalesItems> itensVenda;

    @OneToMany(mappedBy = "product")
    private List<Reviews> reviews;

    public Product() {}

    public Product(Integer id) {
        this.id = id;
    }

    public void addImages(List<String> urls) {
        if (urls == null) return;
        for (String url : urls) {
            ProductImages productImages = new ProductImages();
            productImages.setImagem(url);
            productImages.setProduct(this);
            productImages.setCapaProduto(false);
            images.add(productImages);
        }
    }

    public void update(ProductUpdateDTO productDTO) {
        if (productDTO.name() != null) {
            this.nome = productDTO.name();
        }
        if (productDTO.description() != null) {
            this.descricao = productDTO.description();
        }
        if (productDTO.stock() != null) {
            this.estoque = productDTO.stock();
        }
        if (productDTO.category() != null) {
            this.categoria = productDTO.category();
        }
        if (productDTO.price() != null) {
            this.preco = productDTO.price();
        }
        if (productDTO.color() != null) {
            this.cor = productDTO.color();
        }
        if (productDTO.cover() != null) {
            ProductImages newCover = new ProductImages();
            newCover.setCapaProduto(true);
            newCover.setProduct(this);
            newCover.setImagem(productDTO.cover());
            images.add(newCover);
        }
        if (productDTO.images() != null && !productDTO.images().isEmpty()) {
            productDTO.images().forEach(img -> images.add(new ProductImages(img, this, false)));
        }
    }
}