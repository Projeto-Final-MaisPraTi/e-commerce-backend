package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;

public record ProductDTO(
        Integer id,
        String nome,
        int nota,
        Double preco,
        String descricao,
        String images
) {
    public ProductDTO(Product product, String cover) {
        this(product.getId(), product.getNome(), product.getNota(), product.getPreco(), product.getDescricao(),cover);
    }

    public ProductDTO(Product product) {
        this(product.getId(), product.getNome(), product.getNota(), product.getPreco(), product.getDescricao(), null);
    }
}
