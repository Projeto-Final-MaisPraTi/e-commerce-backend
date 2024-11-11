package com.ecommerce.app.infra.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public enum Roles implements List<GrantedAuthority> {

    CLIENT,
    ADMIN;

    public String getAuthority(){
        return "ROLE_" + this.name();
    }


}
