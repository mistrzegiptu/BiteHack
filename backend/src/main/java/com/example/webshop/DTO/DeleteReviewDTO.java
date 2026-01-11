package com.example.webshop.DTO;

import java.util.UUID;

public record DeleteReviewDTO(
        UUID reviewId,
        UUID productId,
        int rating,
        String text,
        String userLogin
) {
}
