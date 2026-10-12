
package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.dto.WeakTopicResponse;
import com.example.aiExamAssistant.model.User;
import com.example.aiExamAssistant.repository.UserRepository;
import com.example.aiExamAssistant.service.WeaknessDetectionService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/weakness")
public class WeaknessDetectionController {

    private final WeaknessDetectionService weaknessService;
    private final UserRepository userRepository;

    public WeaknessDetectionController(
            WeaknessDetectionService weaknessService,
            UserRepository userRepository) {
        this.weaknessService = weaknessService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<WeakTopicResponse> getMyWeaknesses() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User student = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Authenticated student was not found"
                ));

        return weaknessService.getStudentPerformance(
                student.getId()
        );
    }
}
