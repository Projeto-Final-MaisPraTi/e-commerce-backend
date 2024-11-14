package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateDTO(
        Integer id,
        @NotBlank(message = "O nome do produto não pode estar em branco")
        String nome,
        @NotNull(message = "O preço não pode estar em branco")
        @PositiveOrZero(message = "O preço deve ser positivo")
        Double preco,
        String cor,
        String descricao,
        @NotNull(message = "O estoque não pode estar em branco")
        @PositiveOrZero(message = "O estoque deve ser positivo")
        Integer estoque,
        String categoria,
        String cover,
        List<String> images
) {
    public ProductUpdateDTO(Product product, String cover, List<String> images) {
        this(product.getId(), product.getNome(), product.getPreco(), product.getCor(), product.getDescricao(), product.getEstoque(), product.getCategoria(), cover, images);
    }
}
