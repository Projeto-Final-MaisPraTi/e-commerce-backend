package com.ecommerce.app.dto.itemCart;

import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.dto.user.UserDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDetailsDTO {
    private Integer id;

    @NotNull(message = "Os detalhes do produto não podem estar em branco")
    private ProductDTO productDTO;

    @NotNull(message = "A quantidade não pode estar em branco")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    @Max(value = 100, message = "A quantidade máxima permitida é 100")
    private Integer quantity;

    private Double preco; // Valor total do produto multiplicado pela quantidade
    private Integer descount;
}
