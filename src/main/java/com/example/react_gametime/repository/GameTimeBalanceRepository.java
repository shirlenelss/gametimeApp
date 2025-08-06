package com.example.react_gametime.repository;

import com.example.react_gametime.model.GameTimeBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTimeBalanceRepository extends JpaRepository<GameTimeBalance, Long> {}