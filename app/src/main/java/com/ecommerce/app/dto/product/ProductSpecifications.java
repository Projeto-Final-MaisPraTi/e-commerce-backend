package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    // Specifications constroi as consultas sql para ser usando no findAll no Repository
    // root(Quer dizer a propria classe que foi passada no Specification, no caso o Product,
    // que é nossa entidade, vamos fazer consultas em cima dela)
    // o query contem algumas opções para construir as querys
    // o criterualBuild tambem contem algumas opções para que seja possivel construir as querys
    // o lado esquerdo você diz onde quer buscar, e o direito você diz o que quer buscar
    // o root.get("") voce coloca o nome da variavel na entidade a qual você deseja buscar algo

    public static Specification<Product> hasName(String name) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder
                        .like(criteriaBuilder.lower(root.get("nome")),
                                "%" + name.toLowerCase() + "%"));
    }

    public static Specification<Product> hasCategory(String category) {
            return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoria"), category));
    }

    public static Specification<Product> priceGreaterThan(Double minPrice) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), minPrice));
    }

    public static Specification<Product> priceLessThan(Double maxPrice) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("preco"), maxPrice));
    }

    public static Specification<Product> colorEqualsTo(String color) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cor"), color));
    }
}