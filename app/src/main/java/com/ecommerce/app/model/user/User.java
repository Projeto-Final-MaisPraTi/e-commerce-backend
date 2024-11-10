package com.ecommerce.app.model.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.app.infra.enums.Roles;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.itemCart.ItemCart;
//import com.ecommerce.app.model.role.Role;
import com.ecommerce.app.model.sales.Sales;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Table(name = "usuarios")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeRole", nullable = false)
    private Roles typeRole; // "CLIENT", "ADMIN"
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> address;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Sales> sales;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ItemCart> itemCart;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return List.of(() -> "ROLE_USER");
//        return typeRole.name().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
