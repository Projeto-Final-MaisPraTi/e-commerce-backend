package com.ecommerce.app.dto.product;

import com.ecommerce.app.model.product.Product;

import java.text.NumberFormat;

public record ProductDTO(
        Integer id,
        String name,
        int nota,
        Integer discount,
        String preco,
        String descricao,
        String priceDiscount,
        String images
) {
    public ProductDTO(Product product, String cover) {
        this(product.getId(),
                product.getNome(),
                product.getNota(),
                product.getDiscount(),
                getPriceFormat(product.getPreco()),
                product.getDescricao(),
                calculateDiscount(product.getPreco(),
                        product.getDiscount()),cover);
    }

    public ProductDTO(Product product) {
        this(product.getId(), product.getNome(), product.getNota(), product.getDiscount(), getPriceFormat(product.getPreco()), product.getDescricao(),null, null);
    }

    public static String getPriceFormat(Double value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(value);
    }

    public static String calculateDiscount(Double price, Integer discount) {
        if (discount == 0) {
            return null;
        }
        Double value = price - (price / 100) * discount;
        return (getPriceFormat(value));
    }
}
