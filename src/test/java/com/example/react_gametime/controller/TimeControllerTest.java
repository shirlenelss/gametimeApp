package com.example.react_gametime.controller;

import com.example.react_gametime.infrastructure.persistence.*;
import com.example.react_gametime.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.react_gametime.model.Range.DAY;
import static com.example.react_gametime.model.RequestStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TimeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimeRequestRepository timeRequestRepository;

    private TimeRequest timeRequest;
    private Optional<UserEntity> optionalUser;

    @BeforeEach
    void setUp() {
        timeRequestRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity testUser = UserEntityFixture.builder().username("TestUser").role(Role.PARENT).build().toEntity();
        userRepository.saveAndFlush(testUser);
        System.out.println(testUser);

        optionalUser = userRepository.findByUsername("TestUser");
        optionalUser.ifPresentOrElse(user -> {
                System.out.println(user);
                timeRequest = new TimeRequest();
                timeRequest.setRequestedMinutes(30);
                timeRequest.setUser(user);
                timeRequest.setStatus(PENDING);
                timeRequest.setCreatedAt(LocalDateTime.now());
                timeRequestRepository.saveAndFlush(timeRequest);
                System.out.println(timeRequest);
            },
                () -> {
                    throw new RuntimeException("Test user not found");
                }
        );

    }

    @Test
    void testGetPendingRequests() throws Exception {

        mockMvc.perform(get("/api/requests/pending"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requestedMinutes").value(timeRequest.getRequestedMinutes()))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    void testCreateTimeRequest() throws Exception {
        mockMvc.perform(post("/api/requests?minutes="+timeRequest.getRequestedMinutes()+"&userId=" + optionalUser.get().getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestedMinutes").value(timeRequest.getRequestedMinutes()))
                .andExpect(jsonPath("$.userId").value(optionalUser.get().getId()));
    }

    @Test
    void testGetHistoryWithWeekRangeAndDefaultStatus() throws Exception {
        // Arrange: create approved and rejected requests in different time ranges
        LocalDateTime now = LocalDateTime.now();
        UserEntity testUser = optionalUser.orElseThrow(() -> new RuntimeException("Test user not found"));

        //save a pending request
        TimeRequest requestPending = new TimeRequest();
        requestPending.setRequestedMinutes(15);
        requestPending.setStatus(PENDING);
        requestPending.setCreatedAt(now);
        requestPending.setUser(testUser);
        timeRequestRepository.saveAndFlush(requestPending);

        //save a approved request
        TimeRequest requestApproved = new TimeRequest();
        requestApproved.setRequestedMinutes(30);
        requestApproved.setStatus(APPROVED);
        requestApproved.setCreatedAt(now.minusDays(2).withHour(8).withMinute(0));
        requestApproved.setApprovedAt(now.minusDays(2).withHour(8).withMinute(10));
        requestApproved.setUser(testUser);
        timeRequestRepository.saveAndFlush(requestApproved);

        //save a approvedOld request
        TimeRequest approvedOld = new TimeRequest();
        approvedOld.setRequestedMinutes(120);
        approvedOld.setStatus(PENDING);
        requestApproved.setCreatedAt(now.minusWeeks(2).withHour(8).withMinute(0));
        requestApproved.setApprovedAt(now.minusWeeks(2).withHour(8).withMinute(10));
        approvedOld.setUser(testUser);
        timeRequestRepository.saveAndFlush(approvedOld);

        //save a approvedOld request
        TimeRequest rejectedRecent = new TimeRequest();
        rejectedRecent.setRequestedMinutes(60);
        rejectedRecent.setStatus(REJECTED);
        rejectedRecent.setCreatedAt(now.withMinute(0));
        rejectedRecent.setApprovedAt(now.withMinute(10));
        rejectedRecent.setUser(testUser);
        timeRequestRepository.saveAndFlush(rejectedRecent);

        // Act & Assert: only approvedRecent should be returned
        mockMvc.perform(get("/api/history")
                .param("range", "WEEK")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(requestApproved.getId()))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetHistoryWithDayRangeAndStatusRejected() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        UserEntity testUser = optionalUser.orElseThrow(() -> new RuntimeException("Test user not found"));
        //save a rejected request today
        TimeRequest rejectedToday = new TimeRequest();
        rejectedToday.setRequestedMinutes(60);
        rejectedToday.setStatus(REJECTED);
        rejectedToday.setCreatedAt(now.withMinute(0));
        rejectedToday.setUser(testUser);
        timeRequestRepository.saveAndFlush(rejectedToday);

        //save a approved request today
        TimeRequest approvedToday = new TimeRequest();
        approvedToday.setRequestedMinutes(60);
        approvedToday.setStatus(APPROVED);
        approvedToday.setCreatedAt(now.withMinute(8));
        approvedToday.setApprovedAt(now.withMinute(10));
        approvedToday.setUser(testUser);
        timeRequestRepository.saveAndFlush(approvedToday);

        mockMvc.perform(get("/api/history")
                .param("range", String.valueOf(DAY))
                .param("status", "REJECTED")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(rejectedToday.getId()))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetHistoryWithInvalidRange() throws Exception {
        mockMvc.perform(get("/time/history")
                .param("range", "NONE")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}