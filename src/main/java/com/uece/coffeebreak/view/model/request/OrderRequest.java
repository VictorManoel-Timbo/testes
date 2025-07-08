package com.uece.coffeebreak.view.model.request;

import com.uece.coffeebreak.entity.enums.OrderStatus;
import com.uece.coffeebreak.entity.enums.WithdrawalMethod;

import java.time.Instant;
import java.util.List;

public class OrderRequest {
    private Instant moment;
    private OrderStatus status;
    private WithdrawalMethod withdrawalMethod;
    private Long clientId;
    private List<OrderProductRequest> items;
    private PaymentRequest payment;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public WithdrawalMethod getWithdrawalMethod() {
        return withdrawalMethod;
    }

    public void setWithdrawalMethod(WithdrawalMethod withdrawalMethod) {
        this.withdrawalMethod = withdrawalMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderProductRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderProductRequest> items) {
        this.items = items;
    }

    public PaymentRequest getPayment() {
        return payment;
    }

    public void setPayment(PaymentRequest payment) {
        this.payment = payment;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }
}
