package com.example.webshop.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity(name = "Orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private UUID id;

    @Column(name = "user_id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderId")
    private List<OrderDetail> orders;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public List<OrderDetail> getOrders() {
        return orders;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
