package com.example.react_gametime.application.mapper;

import com.example.react_gametime.application.dto.TimeRequest;

public class TimeRequestMapper {
    public static TimeRequest toDto(com.example.react_gametime.infrastructure.persistence.TimeRequest entity) {
        if (entity == null) return null;
        return new TimeRequest(
                entity.getId(),
            entity.getRequestedMinutes(),
            entity.getUser().getId(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getApprovedAt()
        );
    }
}
