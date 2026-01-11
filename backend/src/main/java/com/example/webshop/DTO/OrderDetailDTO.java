package com.example.webshop.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDetailDTO(UUID productId, String productName, String imagePath, long amount, BigDecimal lineTotalPrice) {
}