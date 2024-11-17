package com.ecommerce.app.utils;

import java.text.NumberFormat;

public class CurrencyUtils {

    //    Método de formatação do valor
    public static Double formatValue(Double price) {
        if (price == null) return null;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return Double.valueOf(currencyFormat.format(price));
    }

    //    Método para calcular o desconto formatado
    public static Double calculateDiscount(Double price, Integer discount) {
        if (price == null || discount == null || discount == 0) {
            return null;
        }
        Double discountedPrice = price - (price * discount / 100);
        return formatValue(discountedPrice);
    }
}
