package com.ecommerce.app.dto.reviews;

import lombok.Data;

@Data
public class ReviewsDTO {
    private Integer id;
    private int avaliacao;
    private Integer id_produto;
    private String username;
}