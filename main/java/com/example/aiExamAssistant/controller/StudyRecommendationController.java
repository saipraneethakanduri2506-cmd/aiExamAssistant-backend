
package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.model.User;
import com.example.aiExamAssistant.repository.UserRepository;
import com.example.aiExamAssistant.service.StudyRecommendationService;
import com.example.aiExamAssistant.dto.StudyRecommendationResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class StudyRecommendationController {

    private final StudyRecommendationService recommendationService;
    private final UserRepository userRepository;

    public StudyRecommendationController(
            StudyRecommendationService recommendationService,
            UserRepository userRepository) {

        this.recommendationService = recommendationService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<StudyRecommendationResponse> getRecommendations() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User student = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Authenticated student was not found"
                ));

        return recommendationService.getRecommendations(
                student.getId()
        );
    }
}
