package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.dto.DashboardResponse;
import com.example.aiExamAssistant.model.User;
import com.example.aiExamAssistant.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/dashboard")
public class DashboardController {

    private final AuthService authService;

    public DashboardController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            Authentication authentication) {

        User user = authService.getUserByEmail(
                authentication.getName());

        DashboardResponse response = new DashboardResponse(
                user.getFullName(),
                user.getStream().getName(),
                user.getExam().getName(),
                user.getTargetExamYear(),
                user.getDailyStudyMinutes(),
                user.getGoal(),
                "Welcome back! Continue your exam preparation."
        );

        return ResponseEntity.ok(response);
    }
}