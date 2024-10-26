package com.ecommerce.app.dto.itemCart;

import com.ecommerce.app.dto.product.ProductDTO;

import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartDTO {
    private Long id;
    private ProductDTO productDTO;
    private Integer quantidade;
    private Double preco; // Valor total do produto multiplicado pela quantidade
    private UserDTO userDTO;
}
