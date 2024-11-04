package com.ecommerce.app.infra.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserIdentification {

    private Long id;
    private String username;
    private String login;
    private List<String> permissions;

    public UserIdentification(Long id, String username, String login, List<String> permissions) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.permissions = permissions;
    }

    public List<String> getPermissions() {

        if(permissions == null){
            permissions = new ArrayList<>();
        }

        return permissions;
    }
}
