package com.ecommerce.app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private int id;
    private String name;
    private int rating;
    private String price;
    private String priceDiscount;
    public String categoria;
    private String description;
    private Integer estoque;
    private Integer discount;
    private Boolean flashSale;
    private String color;
    private List<String> images;
}
