package com.example.react_gametime.application.dto;

import com.example.react_gametime.model.RequestStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TimeRequest {
    private Long id;
    @NotNull
    private int requestedMinutes;
    @NotNull
    private long userId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

}
