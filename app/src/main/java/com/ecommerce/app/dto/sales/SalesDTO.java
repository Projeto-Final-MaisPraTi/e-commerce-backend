package com.ecommerce.app.dto.sales;

import java.util.List;

import com.ecommerce.app.dto.coupons.CouponsDTO;
import com.ecommerce.app.dto.payment.PaymentDTO;
import com.ecommerce.app.dto.saleStatus.SaleStatusDTO;
import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.dto.user.UserDTO;

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
