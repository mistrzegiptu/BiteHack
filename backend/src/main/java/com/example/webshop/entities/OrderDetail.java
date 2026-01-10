package com.example.webshop.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "OrderDetails")
public class OrderDetail {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private long amount;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public OrderDetail() {
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public long getAmount() {
        return amount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
