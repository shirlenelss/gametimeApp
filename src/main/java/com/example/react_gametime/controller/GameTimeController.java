package com.example.react_gametime.controller;

import com.example.react_gametime.model.GameTimeBalance;
import com.example.react_gametime.model.GameTimeRequest;
import com.example.react_gametime.service.GameTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameTimeController {

    @Autowired
    private GameTimeService gameTimeService;

    // Son creates a new request
    @PostMapping("/requests")
    public ResponseEntity<GameTimeRequest> createRequest(@RequestParam Long userId,
                                                         @RequestParam int minutes) {
        GameTimeRequest req = gameTimeService.createRequest(userId, minutes);
        return ResponseEntity.ok(req);
    }

    // Mum fetches pending requests
    @GetMapping("/requests/pending")
    public ResponseEntity<List<GameTimeRequest>> getPendingRequests() {
        return ResponseEntity.ok(gameTimeService.getPendingRequests());
    }

    // Mum approves a request
    @PostMapping("/requests/{id}/approve")
    public ResponseEntity<GameTimeRequest> approveRequest(@PathVariable Long id) {
        GameTimeRequest req = gameTimeService.approveRequest(id);
        return ResponseEntity.ok(req);
    }

    // Mum rejects a request
    @PostMapping("/requests/{id}/reject")
    public ResponseEntity<GameTimeRequest> rejectRequest(@PathVariable Long id) {
        GameTimeRequest req = gameTimeService.rejectRequest(id);
        return ResponseEntity.ok(req);
    }

    // Get game time balance for user
    @GetMapping("/balance")
    public ResponseEntity<GameTimeBalance> getBalance(@RequestParam Long userId) {
        return ResponseEntity.ok(gameTimeService.getBalance(userId));
    }
}
