package com.uece.coffeebreak.entity.enums;

public enum WithdrawalMethod {

    PICKUP(0),
    DELIVERY(1);

    private int code;
    private WithdrawalMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static WithdrawalMethod valueOf(int code) {
        for (WithdrawalMethod withdrawalMethod : WithdrawalMethod.values()) {
            if (withdrawalMethod.getCode() == code) {
                return withdrawalMethod;
            }
        }
        throw new IllegalArgumentException("No withdrawal method with code " + code);
    }
}
