package com.uece.coffeebreak.entity.enums;

public enum StockType {

    DRY(0),
    REFRIGERATOR(1),
    FREEZER(2);

    private int code;
    private StockType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StockType valueOf(int code) {
        for (StockType type : StockType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("No stock type with code " + code);
    }
}
