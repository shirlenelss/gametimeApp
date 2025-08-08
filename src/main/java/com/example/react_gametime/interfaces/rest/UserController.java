package com.example.react_gametime.interfaces.rest;

import com.example.react_gametime.domain.model.User;
import com.example.react_gametime.service.UserService;
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
    public ResponseEntity<User> addUser(@PathVariable String userName) {
        Optional<User> user = userService.findByUsername(userName);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User created = userService.addUser(user);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}