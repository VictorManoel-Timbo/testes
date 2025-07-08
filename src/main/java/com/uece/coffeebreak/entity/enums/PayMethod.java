package com.uece.coffeebreak.entity.enums;

public enum PayMethod {

    PIX(0),
    TICKET(1),
    CREDIT(2),
    DEBIT(3);

    private int code;
    private PayMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PayMethod valueOf(int code) {
        for (PayMethod payMethod : PayMethod.values()) {
            if (payMethod.getCode() == code) {
                return payMethod;
            }
        }
        throw new IllegalArgumentException("No pay method with code " + code);
    }
}
