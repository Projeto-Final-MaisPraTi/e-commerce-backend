package com.ecommerce.app.dto.reviews;

import lombok.Data;

@Data
public class ReviewsDTO {
    private Integer id;
    private int avaliacao;
    private Integer productId;
    private Integer userId;
    private String username;
}