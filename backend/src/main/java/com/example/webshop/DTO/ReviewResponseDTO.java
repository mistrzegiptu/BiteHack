package com.example.webshop.DTO;

import java.util.UUID;

public record ReviewResponseDTO(
        UUID id,
        int rating,
        String text,
        String login
) {
}
