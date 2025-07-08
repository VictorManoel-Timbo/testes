package com.uece.coffeebreak.entity.enums;

public enum Available {

    AVAILABLE(0),
    UNAVAILABLE(1);

    private int code;
    private Available(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Available valueOf(int code) {
        for (Available available : Available.values()) {
            if (available.getCode() == code) {
                return available;
            }
        }
        throw new IllegalArgumentException("No available with code " + code);
    }
}
