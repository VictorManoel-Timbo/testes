package com.uece.coffeebreak.view.model.response;

import com.uece.coffeebreak.entity.enums.Role;

public class AuthResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String country;
    private Role role;
    private String token;

    public AuthResponse(String token, String email, String phone, String country, Role role, Long id, String name) {
        this.token = token;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.role = role;
        this.id = id;
        this.name = name;
    }
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
