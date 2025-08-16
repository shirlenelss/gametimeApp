package com.example.react_gametime.application.service;

import com.example.react_gametime.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.example.react_gametime.infrastructure.persistence.UserEntity;

import javax.swing.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> login(String username, String password) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        System.out.println("UserService.login: " + user);
        if (user.isPresent() && user.get().getPasswordHash().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
}