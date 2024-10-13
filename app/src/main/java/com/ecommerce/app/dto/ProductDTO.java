package com.ecommerce.app.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
public class ProductDTO {
    private int id;

    private String nome;

    private int nota;

    private Double preco;

    private String cor;

    private String descricao;

    private int estoque;

    private String categoria;

    private List<String> images;
}
