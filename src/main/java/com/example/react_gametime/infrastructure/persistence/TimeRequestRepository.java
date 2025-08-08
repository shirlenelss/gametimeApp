package com.example.react_gametime.infrastructure.persistence;

import com.example.react_gametime.domain.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRequestRepository extends JpaRepository<TimeRequest, Long> {
    List<TimeRequest> findByStatus(RequestStatus status);
}
