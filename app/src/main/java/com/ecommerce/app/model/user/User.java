package com.ecommerce.app.model.user;

import java.util.List;

import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.sales.Sales;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "role", nullable = false)
    private String role;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Sales> sales;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ItemCart> itemCart;

}
