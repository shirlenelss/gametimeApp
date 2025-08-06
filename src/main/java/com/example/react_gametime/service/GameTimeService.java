package com.example.react_gametime.service;

import com.example.react_gametime.model.GameTimeBalance;
import com.example.react_gametime.model.GameTimeRequest;
import com.example.react_gametime.model.RequestStatus;
import com.example.react_gametime.model.User;
import com.example.react_gametime.repository.GameTimeBalanceRepository;
import com.example.react_gametime.repository.GameTimeRequestRepository;
import com.example.react_gametime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameTimeService {

    @Autowired
    private GameTimeRequestRepository requestRepo;

    @Autowired
    private GameTimeBalanceRepository balanceRepo;

    @Autowired
    private UserRepository userRepo;

    public GameTimeRequest createRequest(Long userId, int minutes) {
        User user = userRepo.findById(userId).orElseThrow();
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
