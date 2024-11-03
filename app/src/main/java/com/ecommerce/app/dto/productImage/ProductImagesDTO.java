package com.ecommerce.app.dto.productImage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImagesDTO {
    private Integer id;
    private String imagem;
    private Integer id_produto;
}
