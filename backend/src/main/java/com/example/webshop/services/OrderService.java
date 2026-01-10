package com.example.webshop.services;

import com.example.webshop.entities.Order;
import com.example.webshop.entities.Product;
import com.example.webshop.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> getOrdersByUserId(User user) {
        return entityManager.createQuery("SELECT o FROM Orders o WHERE o.user = :user", Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Product> getProductsInOrder(Order order) {
        return entityManager.createQuery("SELECT od.product FROM OrderDetails od WHERE od.order = :order", Product.class)
                .setParameter("order", order)
                .getResultList();
    }

    public List<Order> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end) {
        return entityManager.createQuery("SELECT o FROM Orders o WHERE o.orderDateTime BETWEEN :start AND :end", Order.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public BigDecimal getTotalRevenue() {
        return entityManager.createQuery(
                        "SELECT SUM(o.totalPrice) FROM Orders o", BigDecimal.class)
                .getSingleResult();
    }

    @Transactional
    public void addOrder(Order order) { //TODO: Refactor
        entityManager.persist(order);
    }
}
