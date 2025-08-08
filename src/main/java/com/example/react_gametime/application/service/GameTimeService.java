package com.example.react_gametime.application.service;

import com.example.react_gametime.infrastructure.persistence.GameTimeBalance;
import com.example.react_gametime.infrastructure.persistence.GameTimeRequest;
import com.example.react_gametime.domain.model.RequestStatus;
import com.example.react_gametime.infrastructure.persistence.GameTimeBalanceRepository;
import com.example.react_gametime.infrastructure.persistence.GameTimeRequestRepository;
import com.example.react_gametime.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.react_gametime.infrastructure.persistence.User;

@Service
public class GameTimeService {

    @Autowired
    private GameTimeRequestRepository requestRepo;

    @Autowired
    private GameTimeBalanceRepository balanceRepo;

    @Autowired
    private UserRepository userRepositoryRepo;

    public GameTimeRequest createRequest(Long userId, int minutes) {
        User user = userRepositoryRepo.findById(userId).orElseThrow();
        GameTimeRequest req = new GameTimeRequest();
        req.setUser(user);
        req.setRequestedMinutes(minutes);
        req.setStatus(RequestStatus.PENDING);
        req.setCreatedAt(LocalDateTime.now());
        return requestRepo.save(req);
    }

    public List<GameTimeRequest> getPendingRequests() {
        return requestRepo.findByStatus(RequestStatus.PENDING);
    }

    public GameTimeRequest approveRequest(Long requestId) {
        GameTimeRequest req = requestRepo.findById(requestId).orElseThrow();
        req.setStatus(RequestStatus.APPROVED);
        req.setApprovedAt(LocalDateTime.now());
        requestRepo.save(req);

        GameTimeBalance balance = balanceRepo.findById(req.getUser().getId())
                .orElse(new GameTimeBalance());
        balance.setUserId(req.getUser().getId());
        balance.setTotalAllowedMinutes(
                (balance.getTotalAllowedMinutes() == 0 ? 0 : balance.getTotalAllowedMinutes())
                        + req.getRequestedMinutes());
        balance.setLastUpdated(LocalDateTime.now());
        balanceRepo.save(balance);

        return req;
    }

    public GameTimeRequest rejectRequest(Long requestId) {
        GameTimeRequest req = requestRepo.findById(requestId).orElseThrow();
        req.setStatus(RequestStatus.REJECTED);
        req.setApprovedAt(LocalDateTime.now());
        return requestRepo.save(req);
    }

    public GameTimeBalance getBalance(Long userId) {
        return balanceRepo.findById(userId).orElse(new GameTimeBalance());
    }
}
