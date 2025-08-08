package com.example.react_gametime.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeBalanceRepository extends JpaRepository<TimeBalance, Long> {}