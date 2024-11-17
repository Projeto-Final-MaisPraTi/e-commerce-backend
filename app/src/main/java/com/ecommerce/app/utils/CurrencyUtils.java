package com.ecommerce.app.utils;

import java.text.NumberFormat;

public class CurrencyUtils {

    //    Método de formatação do valor
    public static String formatValue(Double price) {
        if (price == null) return null;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(price);
    }

    //    Método para calcular o desconto formatado
    public static String calculateDiscount(Double price, Integer discount) {
        if (price == null || discount == null || discount == 0) {
            return null;
        }
        Double discountedPrice = price - (price * discount / 100);
        return formatValue(discountedPrice);
    }
}
