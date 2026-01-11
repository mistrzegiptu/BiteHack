package com.example.webshop.DTO;

public record UserUpdateDTO(
        String firstname,
        String lastname,
        String country,
        String city,
        String street,
        String postalNumber,
        String phoneNumber,
        String mail,
        String login,
        String password
) {
}
