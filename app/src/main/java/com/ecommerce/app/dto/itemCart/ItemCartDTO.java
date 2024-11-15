package com.ecommerce.app.dto.itemCart;

import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartDTO {
    private Integer id;

    @NotNull(message = "Os detalhes do produto não podem estar em branco")
    private ProductDetailsDTO productDetailsDTO;

    @NotNull(message = "A quantidade não pode estar em branco")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    @Max(value = 100, message = "A quantidade máxima permitida é 100")
    private Integer quantidade;

    private Double preco; // Valor total do produto multiplicado pela quantidade

    @NotNull(message = "O usuário não pode estar em branco")
    private UserDTO userDTO;
}
