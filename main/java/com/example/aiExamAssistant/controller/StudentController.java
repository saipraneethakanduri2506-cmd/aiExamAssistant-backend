package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.dto.StudentProfileResponse;
import com.example.aiExamAssistant.model.User;
import com.example.aiExamAssistant.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final AuthService authService;

    public StudentController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileResponse> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        User user = authService.getUserByEmail(email);

        StudentProfileResponse response =
                new StudentProfileResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getMobile(),
                        user.getStream().getName(),
                        user.getExam().getName(),
                        user.getIntermediateYear(),
                        user.getTargetExamYear(),
                        user.getDailyStudyMinutes(),
                        user.getGoal()
                );

        return ResponseEntity.ok(response);
    }
}