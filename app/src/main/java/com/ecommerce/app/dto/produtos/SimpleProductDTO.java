package com.ecommerce.app.dto.produtos;

import com.ecommerce.app.model.Product;

public record SimpleProductDTO(
        int id,
        String nome,
        int nota,
        Double preco,
        String images
) {
    public SimpleProductDTO(Product product) {
        this(product.getId(), product.getNome(), product.getNota(), product.getPreco(), product.getImages().get(0).getImagem());
    }
}
