package com.ecommerce.app.dto.produtos;

import java.util.List;

public record ProductUpdateDTO(Integer id,
                               String nome,
                               Double preco,
                               String cor,
                               String descricao,
                               Integer estoque,
                               String categoria,
                               String cover,
                               List<String> images) {
}
