package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;

import java.util.List;

public record ProductUpdateDTO(Integer id,
                               String name,
                               Double price,
                               String color,
                               String description,
                               Integer stock,
                               String category,
                               String cover,
                               List<String> images) {
    public ProductUpdateDTO(Product product, String cover, List<String> images) {
        this(product.getId(), product.getNome(), product.getPreco(), product.getCor(), product.getDescricao(), product.getEstoque(), product.getCategoria(), cover, images);
    }
}