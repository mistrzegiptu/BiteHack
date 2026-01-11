package com.example.webshop.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID orderId,
        LocalDateTime orderDateTime,
        BigDecimal totalPrice,
        List<OrderDetailDTO> items
) {}
