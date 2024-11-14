package com.ecommerce.app.dto.coupons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponsDTO {
    private Integer id;
    private String codigo;
    private Double desconto;
    private LocalDate data_inicial;
    private LocalDate data_final;
    private Boolean expirado;
}