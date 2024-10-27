package com.ecommerce.app.dto.user;

import java.util.List;

import com.ecommerce.app.dto.address.AddressDTO;
import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.dto.sales.SalesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role;
    private List<AddressDTO> address;
    private List<SalesDTO> sales;
    private List<ItemCartDTO> itemCart;
}
