package com.example.react_gametime.application.service;

import com.example.react_gametime.infrastructure.persistence.TimeBalance;
import com.example.react_gametime.infrastructure.persistence.TimeRequest;
import com.example.react_gametime.domain.model.RequestStatus;
import com.example.react_gametime.infrastructure.persistence.TimeBalanceRepository;
import com.example.react_gametime.infrastructure.persistence.TimeRequestRepository;
import com.example.react_gametime.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.react_gametime.infrastructure.persistence.UserEntity;

@Service
public class TimeService {

    @Autowired
    private TimeRequestRepository requestRepo;

    @Autowired
    private TimeBalanceRepository balanceRepo;

    @Autowired
    private UserRepository userRepositoryRepo;

    public TimeRequest createRequest(Long userId, int minutes) {
        UserEntity user = userRepositoryRepo.findById(userId).orElseThrow();
        TimeRequest req = new TimeRequest();
        req.setUser(user);
        req.setRequestedMinutes(minutes);
        req.setStatus(RequestStatus.PENDING);
        req.setCreatedAt(LocalDateTime.now());
        return requestRepo.save(req);
    }

    public List<TimeRequest> getPendingRequests() {
        return requestRepo.findByStatus(RequestStatus.PENDING);
    }

    public TimeRequest approveRequest(Long requestId) {
        TimeRequest req = requestRepo.findById(requestId).orElseThrow();
        req.setStatus(RequestStatus.APPROVED);
        req.setApprovedAt(LocalDateTime.now());
        requestRepo.save(req);

        TimeBalance balance = balanceRepo.findById(req.getUser().getId())
                .orElse(new TimeBalance());
        balance.setUserId(req.getUser().getId());
        balance.setTotalAllowedMinutes(
                (balance.getTotalAllowedMinutes() == 0 ? 0 : balance.getTotalAllowedMinutes())
                        + req.getRequestedMinutes());
        balance.setLastUpdated(LocalDateTime.now());
        balanceRepo.save(balance);

        return req;
    }

    public TimeRequest rejectRequest(Long requestId) {
        TimeRequest req = requestRepo.findById(requestId).orElseThrow();
        req.setStatus(RequestStatus.REJECTED);
        req.setApprovedAt(LocalDateTime.now());
        return requestRepo.save(req);
    }

    public TimeBalance getBalance(Long userId) {
        return balanceRepo.findById(userId).orElse(new TimeBalance());
    }
}
