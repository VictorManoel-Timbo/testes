package com.uece.coffeebreak.view.model.response;

import com.uece.coffeebreak.entity.enums.OrderStatus;
import com.uece.coffeebreak.entity.enums.WithdrawalMethod;

import java.time.Instant;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Instant moment;
    private OrderStatus status;
    private WithdrawalMethod withdrawalMethod;
    private Double total;
    private UserResponse client;
    private List<OrderProductResponse> items;
    private PaymentResponse payment;

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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public WithdrawalMethod getWithdrawalMethod() {
        return withdrawalMethod;
    }

    public void setWithdrawalMethod(WithdrawalMethod withdrawalMethod) {
        this.withdrawalMethod = withdrawalMethod;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public UserResponse getClient() {
        return client;
    }

    public void setClient(UserResponse client) {
        this.client = client;
    }

    public List<OrderProductResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderProductResponse> items) {
        this.items = items;
    }

    public PaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(PaymentResponse payment) {
        this.payment = payment;
    }
}
