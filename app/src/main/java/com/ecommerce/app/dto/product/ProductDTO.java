package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductDTO(
        Integer id,
        @NotBlank(message = "O nome do produto não pode estar em branco")
        String name,
        @PositiveOrZero(message = "A nota deve ser positiva ou zero")
        int nota,
        Integer discount,
        @NotNull(message = "O preço não pode estar em branco")
        @PositiveOrZero(message = "O preço deve ser positivo")
        Double preco,
        String descricao,
        String images
) {
    public ProductDTO(Product product, String cover) {
        this(product.getId(), product.getNome(), product.getNota(), product.getDiscount(), product.getPreco(), product.getDescricao(), cover);
    }

    public ProductDTO(Product product) {
        this(product.getId(), product.getNome(), product.getNota(), product.getDiscount(), product.getPreco(), product.getDescricao(), null);
    }
}
