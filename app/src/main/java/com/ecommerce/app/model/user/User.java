package com.ecommerce.app.model.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.app.infra.enums.Role;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.address.Address;
import com.ecommerce.app.model.itemCart.ItemCart;
//import com.ecommerce.app.model.role.Role;
import com.ecommerce.app.model.sales.Sales;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Table(name = "users")
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

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role roles; // "CLIENT", "ADMIN"

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sales> sales;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCart> itemCart;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.roles.name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        Collection<GrantedAuthority> authorities = List.of(authority);
        return authorities;
    }
}
