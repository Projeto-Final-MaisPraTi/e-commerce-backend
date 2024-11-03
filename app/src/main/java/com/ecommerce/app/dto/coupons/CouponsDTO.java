package com.ecommerce.app.dto.coupons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponsDTO {
    private Integer id;
    private String codigo;
    private Double desconto;
}
