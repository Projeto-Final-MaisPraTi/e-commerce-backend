package com.ecommerce.app.dto.payment;

import com.ecommerce.app.model.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Integer id;

    private Integer idSale;

    @NotBlank(message = "O tipo de pagamento não pode estar em branco")
    private String tipo; // "DINHEIRO", "CARTÃO"

    private String status; // "SUCESSO", "FALHA"

    @NotNull(message = "O valor não pode estar em branco")
    @PositiveOrZero(message = "O valor deve ser positivo")
    private Double valor; // Valor total da compra

    @NotNull(message = "O valor da parcela não pode estar em branco")
    @PositiveOrZero(message = "O valor da parcela deve ser positivo")
    private Double valorParcela; // Valor de cada parcela

    private CardDetailsDTO cardDetails;

    // Novo construtor
    public PaymentDTO(Payment payment) {
        if (payment.getId() != null) {
            this.id = payment.getId();
        }
        if (payment.getTipo() != null) {
            this.tipo = payment.getTipo();
        }
        if (payment.getValor() != null) {
            this.valor = payment.getValor();
        }
        if (payment.getValorParcela() != null) {
            this.valorParcela = payment.getValorParcela();
        }
    }
}