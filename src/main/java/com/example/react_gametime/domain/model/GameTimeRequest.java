package com.example.react_gametime.domain.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// GameTimeRequest.java
@Entity
@Table(name = "game_time_requests")
@Data
public class GameTimeRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private int requestedMinutes;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED

    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

    // getters & setters
}

