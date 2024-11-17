package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;

import java.text.NumberFormat;

import com.ecommerce.app.utils.CurrencyUtils;
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
//        @PositiveOrZero(message = "O preço deve ser positivo") Não precisa por ser String
        String price,
        String description,
        String priceDiscount,
        String cover
) {
    public ProductDTO(Product product, String cover) {
        this(product.getId(),
                product.getNome(),
                product.getNota(),
                product.getDiscount(),
                CurrencyUtils.formatValue(product.getPreco()),
                product.getDescricao(),
                CurrencyUtils.calculateDiscount(product.getPreco(),
                        product.getDiscount()),cover);
    }

    public ProductDTO(Product product) {
        this(product.getId(), product.getNome(), product.getNota(), product.getDiscount(), CurrencyUtils.formatValue(product.getPreco()), product.getDescricao(),null, null);
    }
}