package com.uece.coffeebreak.shared;

import com.uece.coffeebreak.entity.enums.OrderStatus;
import com.uece.coffeebreak.entity.enums.WithdrawalMethod;

import java.time.Instant;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;
    private WithdrawalMethod withdrawalMethod;
    private Double total;
    private UserDTO client;
    private List<OrderProductDTO> items;
    private PaymentDTO payment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
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

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrderProductDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderProductDTO> items) {
        this.items = items;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }
}
