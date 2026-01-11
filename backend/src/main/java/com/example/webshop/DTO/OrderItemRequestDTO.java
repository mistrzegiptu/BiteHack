package com.example.webshop.DTO;

import java.util.UUID;

public record OrderItemRequestDTO(
        UUID productId,
        long amount
) {}
