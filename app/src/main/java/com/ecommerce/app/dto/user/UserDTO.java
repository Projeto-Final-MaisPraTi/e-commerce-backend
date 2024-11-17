package com.ecommerce.app.dto.user;

import com.ecommerce.app.infra.enums.Role;
import com.ecommerce.app.model.user.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private Role roles;
    private String password;

    public UserDTO(Integer id, String username, String email, Role roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserDTO(Integer id, String username, String email, Role roles, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    // Novo construtor para aceitar um objeto User
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.password = user.getPassword();
    }
}