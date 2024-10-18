package com.ecommerce.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesItemsDTO {
    private Long id;
    private ProductDTO productDTO;
    private Integer quantidade;
    private Double preco;
}
