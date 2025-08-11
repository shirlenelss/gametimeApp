package com.example.react_gametime.application.mapper;

import com.example.react_gametime.application.dto.User;
import com.example.react_gametime.infrastructure.persistence.UserEntity;

public class UserMapper {

    public static User toDto(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole().name(),
                entity.getVersion()
        );
    }
}
