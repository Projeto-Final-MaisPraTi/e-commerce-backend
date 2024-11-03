package com.ecommerce.app.model.productImages;

import com.ecommerce.app.model.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "product_images")
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image")
    private String imagem;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Setter
    @Column(name = "product_cover")
    private Boolean capaProduto;

    public ProductImages() {};

    public ProductImages(String imagem, Product product, Boolean capaProduto) {
        this.imagem = imagem;
        this.product = product;
        this.capaProduto = capaProduto;
    }
}
