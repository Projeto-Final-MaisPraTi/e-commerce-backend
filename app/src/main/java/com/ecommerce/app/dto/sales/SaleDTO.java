package com.ecommerce.app.dto.sales;

import java.util.List;

import com.ecommerce.app.dto.coupons.CouponsDTO;
import com.ecommerce.app.dto.itemCart.ItemCartDetailsDTO;
import com.ecommerce.app.dto.payment.PaymentDTO;
import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.dto.user.UserDTO;

import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.itemCart.ItemCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Integer idSale;
    private Double total;
    private List<ItemCartDetailsDTO> itemsCart;
}
