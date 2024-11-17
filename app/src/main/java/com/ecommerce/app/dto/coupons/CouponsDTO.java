package com.ecommerce.app.dto.coupons;

import com.ecommerce.app.model.coupons.Coupons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponsDTO {
    private Integer id;

    @NotBlank(message = "O código do cupom não pode estar em branco")
    private String codigo;

    @NotNull(message = "O desconto não pode estar em branco")
    private Double desconto;

    @NotNull(message = "A data inicial não pode estar em branco")
    private LocalDate data_inicial;

    @NotNull(message = "A data final não pode estar em branco")
    private LocalDate data_final;

    private Boolean expirado;

    // Novo construtor
    public CouponsDTO(Coupons coupons) {
        this.id = coupons.getId();
        this.codigo = coupons.getCodigo();
        this.desconto = coupons.getDesconto_porcentagem();
        this.data_inicial = coupons.getData_inicial();
        this.data_final = coupons.getFinalDate();
        this.expirado = LocalDate.now().isAfter(coupons.getFinalDate());
    }
}
