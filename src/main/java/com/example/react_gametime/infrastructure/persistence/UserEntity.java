package com.example.react_gametime.infrastructure.persistence;

import com.example.react_gametime.domain.model.Role;
import jakarta.persistence.*;
import lombok.Data;

// User.java
@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role; // SON or MUM

    private String email;

    @Version
    private long version;
    // getters & setters
}

