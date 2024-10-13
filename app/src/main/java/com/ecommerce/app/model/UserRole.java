package com.ecommerce.app.model;

public enum UserRole {
    ADMIN("admin"), client("client");

    private String cargo;

    UserRole(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
