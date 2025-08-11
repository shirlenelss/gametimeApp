package com.example.react_gametime.infrastructure.persistence;
import com.example.react_gametime.model.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// GameTimeRequest.java
@Entity
@Table(name = "game_time_requests")
@Data
public class TimeRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    private int requestedMinutes;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED

    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

    // getters & setters
}

