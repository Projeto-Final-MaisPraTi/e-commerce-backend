package com.ecommerce.app.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Integer id;
    private String tipo; // "DINHEIRO", "CARTÃO"
    private String nomeDoDono;
    private String numeroCartao;
    private String validade; // Formato MM/AA
    private String cvc;
    private String status; // "SUCESSO", "FALHA"
    private Integer parcelas; // Número de parcelas
    private Double juros; // Juros
    private Double valor; // Valor total da compra
    private Double valorParcela; // Valor de cada parcela
}
