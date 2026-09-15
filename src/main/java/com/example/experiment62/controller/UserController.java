package com.example.experiment62.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.experiment62.dto.UserRequest;
import com.example.experiment62.model.User;
import com.example.experiment62.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    // Normal Query
    @GetMapping("/normal")
    public ResponseEntity<List<User>> getUsersNormal() {

        return ResponseEntity.ok(
                userService.getUsersNormal()
        );
    }

    // Optimized Query
    @GetMapping("/optimized")
    public ResponseEntity<List<User>> getUsersOptimized() {

        return ResponseEntity.ok(
                userService.getUsersOptimized()
        );
    }

    // Cached Query
    @GetMapping("/cached")
    public ResponseEntity<List<User>> getUsersCached() {

        return ResponseEntity.ok(
                userService.getUsersCached()
        );
    }

    // Native SQL
    @GetMapping("/native")
    public ResponseEntity<List<Object[]>> getUsersNative() {

        return ResponseEntity.ok(
                userService.getUsersNative()
        );
    }

    // Sort by ID
    @GetMapping("/sort/id")
    public ResponseEntity<List<User>> getUsersSortedById() {

        return ResponseEntity.ok(
                userService.getUsersSortedById()
        );
    }

    // Sort by Name
    @GetMapping("/sort/name")
    public ResponseEntity<List<User>> getUsersSortedByName() {

        return ResponseEntity.ok(
                userService.getUsersSortedByName()
        );
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}