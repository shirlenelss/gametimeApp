package com.example.react_gametime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.react_gametime.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
