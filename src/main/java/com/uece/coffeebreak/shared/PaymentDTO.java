package com.uece.coffeebreak.shared;

import com.uece.coffeebreak.entity.enums.PayMethod;

import java.time.Instant;

public class PaymentDTO {
    private Long id;
    private Instant moment;
    private PayMethod method;
    private Integer installments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public PayMethod getMethod() {
        return method;
    }

    public void setMethod(PayMethod method) {
        this.method = method;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }
}
