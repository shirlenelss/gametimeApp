package com.example.react_gametime.infrastructure.persistence;

import com.example.react_gametime.domain.model.GameTimeBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTimeBalanceRepository extends JpaRepository<GameTimeBalance, Long> {}