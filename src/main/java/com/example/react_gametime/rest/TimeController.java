package com.example.react_gametime.rest;

import com.example.react_gametime.infrastructure.persistence.TimeBalance;
import com.example.react_gametime.application.dto.TimeRequest;
import com.example.react_gametime.application.service.TimeService;
import com.example.react_gametime.model.Range;
import com.example.react_gametime.model.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TimeController {

    @Autowired
    private TimeService timeService;

    // Son creates a new request
    @PostMapping("/requests")
    public ResponseEntity<TimeRequest> createRequest(@RequestParam long userId,
                                                     @RequestParam int minutes) {
        TimeRequest req = timeService.createRequest(userId, minutes);
        return ResponseEntity.ok(req);
    }

    // Mum fetches pending requests
    @GetMapping("/requests/pending")
    public ResponseEntity<List<TimeRequest>> getPendingRequests() {
        return ResponseEntity.ok(timeService.getPendingRequestList());
    }

    // Mum approves a request
    @PostMapping("/requests/{id}/approve")
    public ResponseEntity<TimeRequest> approveRequest(@PathVariable Long id) {
        TimeRequest req = timeService.approveRequest(id);
        return ResponseEntity.ok(req);
    }

    // Mum rejects a request
    @PostMapping("/requests/{id}/reject")
    public ResponseEntity<TimeRequest> rejectRequest(@PathVariable Long id) {
        TimeRequest req = timeService.rejectRequest(id);
        return ResponseEntity.ok(req);
    }

    // Get game time balance for user
    @GetMapping("/balance")
    public ResponseEntity<TimeBalance> getBalance(@RequestParam Long userId) {
        return ResponseEntity.ok(timeService.getBalance(userId));
    }

    @GetMapping("/history")
    public List<TimeRequest> getHistory(
            @RequestParam("range") Range range,
            @RequestParam(value = "status", required = false) RequestStatus status
    ) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = switch (range) {
            case DAY -> now.toLocalDate().atStartOfDay();
            case WEEK -> now.minusWeeks(1).toLocalDate().atStartOfDay();
            case MONTH -> now.minusMonths(1).toLocalDate().atStartOfDay();
            default -> throw new IllegalArgumentException("Invalid range: " + range);
        };
        RequestStatus filterStatus = status != null ? status : RequestStatus.APPROVED;
        return timeService.findByCreatedAtAfterAndStatus(from, filterStatus);
    }
}