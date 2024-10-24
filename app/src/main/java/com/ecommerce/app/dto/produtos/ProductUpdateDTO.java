package com.ecommerce.app.dto.produtos;

import com.ecommerce.app.model.Product;

import java.util.List;

public record ProductUpdateDTO(Integer id,
                               String nome,
                               Double preco,
                               String cor,
                               String descricao,
                               Integer estoque,
                               String categoria,
                               String cover,
                               List<String> images) {
    public ProductUpdateDTO(Product product, String cover, List<String> images) {
        this(product.getId(), product.getNome(),
                product.getPreco(), product.getCor(), product.getDescricao(),
                product.getEstoque(), product.getCategoria(), cover, images);
    }
}
