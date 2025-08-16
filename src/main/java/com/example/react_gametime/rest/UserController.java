package com.example.react_gametime.rest;

import com.example.react_gametime.application.dto.User;
import com.example.react_gametime.application.mapper.UserMapper;
import com.example.react_gametime.application.service.UserService;
import com.example.react_gametime.infrastructure.persistence.UserEntity;
import com.example.react_gametime.model.UserPassword;
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
        Optional<UserEntity> byUsername = userService.findByUsername(userName);
        return byUsername
                .map(UserMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody UserEntity user) {
        UserEntity created = userService.addUser(user);
        return ResponseEntity.ok(UserMapper.toDto(created));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserPassword userPassword) {
        Optional<UserEntity> user = userService.login(userPassword.getUsername(), userPassword.getPassword());
        return user
                .map(UserMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}