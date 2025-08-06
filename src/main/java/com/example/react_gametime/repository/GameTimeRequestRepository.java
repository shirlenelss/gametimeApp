package com.example.react_gametime.repository;

import com.example.react_gametime.model.GameTimeRequest;
import com.example.react_gametime.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameTimeRequestRepository extends JpaRepository<GameTimeRequest, Long> {
    List<GameTimeRequest> findByStatus(RequestStatus status);
}
