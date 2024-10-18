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
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role;
    private List<AddressDTO> address;
    private List<SalesDTO> sales;
    private List<ItemCartDTO> itemCart;
}
