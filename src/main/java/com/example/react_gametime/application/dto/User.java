package com.example.react_gametime.application.dto;

public record User(
    Long id,
    String name,
    String email,
    String role,
    Long version
) {
}
