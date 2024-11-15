package com.ecommerce.app.dto.user;

import com.ecommerce.app.infra.enums.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private Role typeRole;
    private String password;

    public UserDTO(Integer id, String username, String email, Role typeRole) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.typeRole = typeRole;
    }

    public UserDTO(Integer id, String username, String email, Role typeRole, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.typeRole = typeRole;
        this.password = password;
    }
}
