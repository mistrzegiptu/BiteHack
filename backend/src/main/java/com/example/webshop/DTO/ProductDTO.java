package com.example.webshop.DTO;

import java.math.BigDecimal;

public record ProductDTO(String name, String categoryName, BigDecimal price, long amount, String imageUrl) {
}
