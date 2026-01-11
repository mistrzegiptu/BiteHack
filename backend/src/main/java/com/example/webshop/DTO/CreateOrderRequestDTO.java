package com.example.webshop.DTO;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequestDTO(
        UUID userId,
        List<OrderItemRequestDTO> items
) {}