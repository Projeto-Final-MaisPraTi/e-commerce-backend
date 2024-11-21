package com.ecommerce.app.dto.sales;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ecommerce.app.dto.coupons.CouponsDTO;
import com.ecommerce.app.dto.itemCart.ItemCartDetailsDTO;
import com.ecommerce.app.dto.payment.PaymentDTO;
import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.dto.user.UserDTO;

import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.sales.Sales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
    private Integer id;
    private Double total;
    private Integer addressId;
    private PaymentDTO payment;
    private CouponsDTO coupons;
    private List<ItemCartDetailsDTO> salesItems;
    private TypeSaleStatus typeSaleStatus;
    private Boolean activeOrder;
    private String date;

    public SalesDTO(Sales sales) {
        this.id = sales.getId();
        this.total = sales.getTotal();
        this.typeSaleStatus = sales.getTypeSaleStatus();
        this.activeOrder = sales.getActiveOrder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = sales.getDate().format(formatter);
        this.date = formattedDate;
    }
}