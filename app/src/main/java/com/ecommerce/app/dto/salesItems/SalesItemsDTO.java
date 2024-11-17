package com.ecommerce.app.dto.salesItems;

import com.ecommerce.app.dto.product.ProductDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesItemsDTO {
    private Integer id;

    @NotNull(message = "Os detalhes do produto não podem estar em branco")
    private ProductDetailsDTO productDetailsDTO;

    @NotNull(message = "A quantidade não pode estar em branco")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "O preço não pode estar em branco")
    @PositiveOrZero(message = "O preço deve ser zero ou positivo")
    private Double preco;
}