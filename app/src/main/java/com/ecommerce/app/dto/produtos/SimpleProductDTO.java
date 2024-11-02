package com.ecommerce.app.dto.produtos;

import com.ecommerce.app.model.Product;

public record SimpleProductDTO(
        int id,
        String nome,
        int nota,
        Double preco,
        String descricao,
        String images
) {
    public SimpleProductDTO(Product product, String cover) {
        this(product.getId(), product.getNome(), product.getNota(), product.getPreco(), product.getDescricao(),cover);
    }
}
