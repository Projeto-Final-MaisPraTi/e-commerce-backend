package com.ecommerce.app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private int id;

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String name;

    @PositiveOrZero(message = "A classificação deve ser positiva ou zero")
    private int rating;

    @NotNull(message = "O preço não pode estar em branco")
    @PositiveOrZero(message = "O preço deve ser positivo")
    private Double price;

    private String priceDiscount;

    @NotBlank(message = "A categoria não pode estar em branco")
    private String categoria;

    private String description;

    @NotNull(message = "O estoque não pode estar em branco")
    @PositiveOrZero(message = "O estoque deve ser positivo")
    private Integer estoque;

    private Integer discount;

    private Boolean flashSale;

    private String color;

    private List<String> images;
}