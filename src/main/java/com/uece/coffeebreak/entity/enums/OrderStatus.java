package com.uece.coffeebreak.entity.enums;

public enum OrderStatus {

    WAITING_PAYMENT(0),
    PAID(1),
    PREPARING(2),
    DELIVERED(3),
    CANCELED(4);

    private int code;
    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus valueOf(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("No order status with code " + code);
    }
}
