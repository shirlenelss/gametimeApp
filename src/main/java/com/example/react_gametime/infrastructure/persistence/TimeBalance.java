package com.example.react_gametime.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

// GameTimeBalance.java
@Entity
@Table(name = "game_time_balances")
@Data
public class TimeBalance {
    @Id
    private Long userId;

    private int totalAllowedMinutes;

    private int totalUsedMinutes;

    private LocalDateTime lastUpdated;

    // getters & setters
}
