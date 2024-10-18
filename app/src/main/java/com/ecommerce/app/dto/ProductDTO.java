package com.ecommerce.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int id;
    private String nome;
    private double preco;
    private String categoria;
    private int nota;
    private String cor;
    private String estoque;
}
