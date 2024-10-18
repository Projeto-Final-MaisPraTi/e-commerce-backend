package com.ecommerce.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
    private Long id;
    private Double total;
    private SaleStatusDTO saleStatus;
    private UserDTO user;
    private PaymentDTO payment;
    private CouponsDTO coupons;
    private List<SalesItemsDTO> salesItems;
}
