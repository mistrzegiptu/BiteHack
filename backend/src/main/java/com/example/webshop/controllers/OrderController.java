package com.example.webshop.controllers;

import com.example.webshop.DTO.CreateOrderRequestDTO;
import com.example.webshop.DTO.OrderDTO;
import com.example.webshop.DTO.ProductDTO;
import com.example.webshop.entities.Order;
import com.example.webshop.services.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable UUID userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        return ResponseEntity.ok(orderService.getTotalRevenue());
    }

    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsInOrder(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderService.getProductsInOrder(order));
    }

    @GetMapping("/filter-date")
    public ResponseEntity<List<OrderDTO>> getOrdersBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(orderService.getOrdersBetweenDates(start, end));
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createOrder(@RequestBody CreateOrderRequestDTO request) {
        UUID newOrderId = orderService.addOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderId);
    }
}