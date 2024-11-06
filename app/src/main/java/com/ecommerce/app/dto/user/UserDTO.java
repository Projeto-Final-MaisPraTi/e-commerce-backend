package com.ecommerce.app.dto.user;

import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.sales.Sales;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;

//    public UserDTO(Long id, String username, String email) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//    }
}
