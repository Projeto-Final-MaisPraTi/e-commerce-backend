package com.ecommerce.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponsDTO {
    private Long id;
    private String codigo;
    private Double desconto;
}
