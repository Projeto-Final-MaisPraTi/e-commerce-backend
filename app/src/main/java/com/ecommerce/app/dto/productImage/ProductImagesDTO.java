package com.ecommerce.app.dto.productImage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImagesDTO {
    private Long id;
    private String imagem;
    private Long id_produto;
}
