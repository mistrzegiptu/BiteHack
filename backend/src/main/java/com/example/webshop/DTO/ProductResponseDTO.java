package com.example.webshop.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, String categoryName, BigDecimal price, long amount, String imageUrl) {
}