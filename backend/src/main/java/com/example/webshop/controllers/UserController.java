package com.example.webshop.controllers;

import com.example.webshop.DTO.UserRegistrationDTO;
import com.example.webshop.DTO.UserUpdateDTO;
import com.example.webshop.entities.User;
import com.example.webshop.services.UserService;
import jakarta.persistence.EntityManager;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register/user")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO dto) {
        var newUser = userService.addUser(dto);
        return ResponseEntity.ok(newUser);
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDTO dto) {
        var newUser = userService.updateUser(dto);
        return newUser != null ? ResponseEntity.ok(newUser) : ResponseEntity.notFound().build();
    }
}