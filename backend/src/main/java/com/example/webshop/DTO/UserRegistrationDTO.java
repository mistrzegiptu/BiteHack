package com.example.webshop.DTO;

import java.util.UUID;

public record UserRegistrationDTO(
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
) {}