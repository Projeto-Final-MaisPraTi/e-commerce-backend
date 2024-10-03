package com.ecommerce.app.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String nome;
    private int nota;
    private double preco;
    private String cor;
}
