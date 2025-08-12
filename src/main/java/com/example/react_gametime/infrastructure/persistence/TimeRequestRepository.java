package com.example.react_gametime.infrastructure.persistence;

import com.example.react_gametime.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRequestRepository extends JpaRepository<TimeRequest, Long> {
    List<TimeRequest> findByStatus(RequestStatus status);
    List<TimeRequest> findByCreatedAtAfterAndStatus(LocalDateTime createdAt, RequestStatus status);
}
