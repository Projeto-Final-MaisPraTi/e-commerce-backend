package com.ecommerce.app.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private int id;
    private String nome;
    private int nota;
    private String preco;
    private String descricao;
    public String categoria;
    private Integer estoque;
    private String cor;
    private List<String> images;
}
