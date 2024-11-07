package com.ecommerce.app.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
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
    public String category;
    private String description;
    private Integer stock;
    private Integer discount;
    @Schema(example = "boolean")
    private Boolean flashSale;
    private String color;
    private List<String> images;
}
