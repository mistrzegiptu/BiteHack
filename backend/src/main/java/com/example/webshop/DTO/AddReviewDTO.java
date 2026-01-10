package com.example.webshop.DTO;

import com.example.webshop.entities.Product;

import java.util.UUID;

public record AddReviewDTO(
    UUID productId,
    int rating,
    String text
)
{}
