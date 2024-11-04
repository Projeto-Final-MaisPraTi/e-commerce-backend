package com.ecommerce.app.dto.salesItems;

import com.ecommerce.app.dto.product.ProductDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesItemsDTO {
    private Integer id;
    private ProductDetailsDTO productDetailsDTO;
    private Integer quantidade;
    private Double preco;
}
