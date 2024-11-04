package com.ecommerce.app.model.product;

import com.ecommerce.app.dto.product.ProductUpdateDTO;
import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.model.reviews.Reviews;
import com.ecommerce.app.model.salesItems.SalesItems;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private Double preco;

    @Column(name = "color", nullable = false)
    private String cor;

    @Column(name = "description", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "stock")
    private Integer estoque;

    @Column(name = "category")
    private String categoria;

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

    public void addImages(List<String> urls) {
        if (urls == null)
            return ;
        for (String url : urls) {
            ProductImages productImages = new ProductImages();
            productImages.setImagem(url);
            productImages.setProduct(this);
            productImages.setCapaProduto(false);
            images.add(productImages);
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
            ProductImages newCover = new ProductImages();
            newCover.setCapaProduto(true);
            newCover.setProduct(this);
            newCover.setImagem(productDTO.cover());
            images.add(newCover);
        }
        if (productDTO.images() != null) {
            if (!productDTO.images().isEmpty()){
                productDTO.images().stream().forEach(img ->
                        images.add(new ProductImages(img, this, false))
                );
            }
        }
    }
}
