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
    private String nome;
    private int nota;
    private Double preco;
    public String categoria;
    private String descricao;
    private Integer estoque;
    private String cor;
    private List<String> images;
}
