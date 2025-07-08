package com.uece.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uece.coffeebreak.entity.pk.OrderProductPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "tb_order_product")
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderProductPK id = new OrderProductPK();

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    private Double discount;
    private String observation;

    public OrderProduct() {}
    public OrderProduct(Order order, Product product, Integer quantity, Double price, Double discount, String observation) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.observation = observation;
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }
    public void setOrder(Order order) {
        id.setOrder(order);
    }
    public Product getProduct() {
        return id.getProduct();
    }
    public void setProduct(Product product) {
        id.setProduct(product);
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getSubTotal() {
        if (discount == null) {
            discount = 0.0;
        }
        return (price * quantity) - discount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderProduct other = (OrderProduct) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
