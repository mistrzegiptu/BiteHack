package com.example.webshop.controllers;

import com.example.webshop.DTO.UserRegistrationDTO;
import com.example.webshop.DTO.UserResponseDTO;
import com.example.webshop.DTO.UserUpdateDTO;
import com.example.webshop.entities.User;
import com.example.webshop.services.UserService;
import jakarta.persistence.EntityManager;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users.stream().map(UserController::mapUser).collect(Collectors.toList()));
    }

    @PostMapping("/register/user")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO dto) {
        var newUser = userService.addUser(dto);
        return ResponseEntity.ok(mapUser(newUser));
    }

    @PatchMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO dto) {
        var newUser = userService.updateUser(dto);
        return newUser != null ? ResponseEntity.ok(mapUser(newUser)) : ResponseEntity.notFound().build();
    }

    private static UserResponseDTO mapUser(User user) {
        return new UserResponseDTO(user.getFirstname(), user.getLastname(), user.getCountry(), user.getCity(), user.getStreet(), user.getPostalNumber(), user.getPhoneNumber(), user.getMail(), user.getLogin(), user.getPasswordHash());
    }
}