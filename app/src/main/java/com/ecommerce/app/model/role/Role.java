//package com.ecommerce.app.model.role;
//
//import com.ecommerce.app.model.user.User;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Entity
//@Data
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name; // nome do tipo de role
//
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
//}
