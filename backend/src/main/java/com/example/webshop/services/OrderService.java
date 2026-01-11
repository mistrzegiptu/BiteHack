package com.example.webshop.services;

import com.example.webshop.DTO.*;
import com.example.webshop.entities.Order;
import com.example.webshop.entities.OrderDetail;
import com.example.webshop.entities.Product;
import com.example.webshop.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    public Order getOrderById(UUID orderId) {
        return entityManager.find(Order.class, orderId);
    }

    public List<OrderDTO> getOrdersByUserId(UUID userId) {
        var orders = entityManager.createQuery("SELECT o FROM Orders o WHERE o.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();

        return orders.stream().map(this::mapToOrderDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsInOrder(Order order) {
        var products = entityManager.createQuery("SELECT od.product FROM OrderDetails od WHERE od.order = :order", Product.class)
                .setParameter("order", order)
                .getResultList();

        return products.stream().map(this::mapToProductDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end) {
        var orders =  entityManager.createQuery("SELECT o FROM Orders o WHERE o.orderDateTime BETWEEN :start AND :end", Order.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        return orders.stream().map(this::mapToOrderDTO).collect(Collectors.toList());
    }

    public BigDecimal getTotalRevenue() {
        return entityManager.createQuery(
                        "SELECT SUM(o.totalPrice) FROM Orders o", BigDecimal.class)
                .getSingleResult();
    }

    @Transactional
    public UUID addOrder(CreateOrderRequestDTO request) { //TODO: Refactor

        User user = entityManager.find(User.class, request.userId());
        if (user == null) {
            throw new IllegalArgumentException("Użytkownik o podanym ID nie istnieje");
        }

        Order order = new Order(user, BigDecimal.ZERO, LocalDateTime.now());

        entityManager.persist(order);

        BigDecimal calculatedTotal = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemRequest : request.items()) {
            Product product = entityManager.find(Product.class, itemRequest.productId());
            if (product == null) {
                throw new IllegalArgumentException("Produkt nie istnieje: " + itemRequest.productId());
            }

            if (product.getAmount() < itemRequest.amount()) {
                throw new IllegalStateException("Za mało produktu w magazynie: " + product.getName());
            }

            product.setAmount(product.getAmount() - itemRequest.amount());

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setAmount(itemRequest.amount());

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.amount()));
            detail.setTotalPrice(lineTotal);

            calculatedTotal = calculatedTotal.add(lineTotal);

            entityManager.persist(detail);
        }

        order.setTotalPrice(calculatedTotal);

        return order.getId();
    }

    private OrderDTO mapToOrderDTO(Order order) {
        List<OrderDetailDTO> items = order.getOrders().stream()
                .map(this::mapToDetailDTO)
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getOrderDateTime(),
                order.getTotalPrice(),
                items
        );
    }

    private OrderDetailDTO mapToDetailDTO(OrderDetail detail) {
        return new OrderDetailDTO(
                detail.getProduct().getProductId(),
                detail.getProduct().getName(),
                detail.getProduct().getImagePath(),
                detail.getAmount(),
                detail.getTotalPrice()
        );
    }

    private ProductDTO mapToProductDTO(Product product) {
        String categoryName = (product.getCategory() != null) ? product.getCategory().getName() : "None";

        return new ProductDTO(
                product.getName(),
                categoryName,
                product.getPrice(),
                product.getAmount(),
                product.getImagePath()
        );
    }
}
