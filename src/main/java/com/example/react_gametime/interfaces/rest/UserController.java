package com.example.react_gametime.interfaces.rest;

import com.example.react_gametime.infrastructure.persistence.UserEntity;
import com.example.react_gametime.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users-by-name/{userName}")
    public ResponseEntity<UserEntity> addUser(@PathVariable String userName) {
        Optional<UserEntity> user = userService.findByUsername(userName);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        UserEntity created = userService.addUser(user);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}