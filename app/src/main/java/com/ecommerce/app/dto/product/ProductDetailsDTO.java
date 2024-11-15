package com.ecommerce.app.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String price;

    private String priceDiscount;

    @NotBlank(message = "A categoria não pode estar em branco")
    private String category;

    private String description;

    @NotNull(message = "O estoque não pode estar em branco")
    @PositiveOrZero(message = "O estoque deve ser positivo")
    private Integer stock;

    private Integer discount;
    @Schema(example = "boolean")

    private Boolean flashSale;

    private String color;

    private List<String> images;
}