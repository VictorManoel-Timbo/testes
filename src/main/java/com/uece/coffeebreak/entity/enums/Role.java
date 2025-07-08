package com.uece.coffeebreak.entity.enums;

public enum Role {

    CLIENT(0),
    ADMIN(1);

    private int code;
    private Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Role valueOf(int code) {
        for (Role role : Role.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role with code " + code);
    }
}
