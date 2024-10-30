package com.ecommerce.app.dto.user;

import java.util.List;

import com.ecommerce.app.dto.address.AddressDTO;
import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.dto.sales.SalesDTO;

import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.sales.Sales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
//    private String password;
//    private String phone;
//    private String role;
//    private List<Address> address;
//    private List<Sales> sales;
//    private List<ItemCart> itemCart;


    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
