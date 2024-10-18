package com.ecommerce.app.dto;

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
}
