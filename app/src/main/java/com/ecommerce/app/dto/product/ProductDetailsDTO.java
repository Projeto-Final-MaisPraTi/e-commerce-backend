package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.productImages.ProductImages;
import com.ecommerce.app.utils.CurrencyUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private int id;

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String name;

//    @PositiveOrZero(message = "A classificação deve ser positiva ou zero")
    private int rating;

    @NotNull(message = "O preço não pode estar em branco")
//    @PositiveOrZero(message = "O preço deve ser positivo") É somente para vizualização
    private String price; // Certifique-se de que o tipo é Double

    private String priceDiscount;

    @NotBlank(message = "A categoria não pode estar em branco")
    private String category;

    private String description;

    @NotNull(message = "O estoque não pode estar em branco")
    @PositiveOrZero(message = "O estoque deve ser positivo")
    private Integer stock;

    private Integer discount;
    private Boolean flashSale;
    private String color;
    private List<String> images;

    // Novo construtor
    public ProductDetailsDTO(Product product) {
        this.id = product.getId();
        this.name = product.getNome();
        this.rating = product.getNota();
        this.price = CurrencyUtils.formatValue(product.getPreco());
        this.category = product.getCategoria();
        this.description = product.getDescricao();
        this.stock = product.getEstoque();
        this.discount = product.getDiscount();
        this.flashSale = product.getFlashSale();
        this.color = product.getCor();
        // Nem sempre a imagem de capa vai ser o indice 0
        this.images = product.getImages().stream().map(ProductImages::getImagem).collect(Collectors.toList());
    }
}