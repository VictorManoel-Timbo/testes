package com.uece.coffeebreak.entity.enums;

public enum IngredientType {

    POWDERED(0),
    LIQUID(1),
    PERISHABLE(2);

    private int code;
    private IngredientType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static IngredientType valueOf(int code) {
        for (IngredientType type : IngredientType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("No ingredient type with code " + code);
    }
}
