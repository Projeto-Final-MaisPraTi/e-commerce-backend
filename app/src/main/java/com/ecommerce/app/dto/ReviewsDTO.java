package com.ecommerce.app.dto;

import lombok.Data;

@Data
public class ReviewsDTO {
    private Long id;
    private int avaliacao;
    private Long id_produto;
    private String username;
}