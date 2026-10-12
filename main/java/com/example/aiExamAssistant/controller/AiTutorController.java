package com.example.aiExamAssistant.controller;
import com.example.aiExamAssistant.dto.TutorAnswerRequest;
import com.example.aiExamAssistant.dto.TutorAnswerResponse;
import com.example.aiExamAssistant.dto.TutorQuestionRequest;
import com.example.aiExamAssistant.dto.TutorResponse;
import com.example.aiExamAssistant.service.AiTutorService;

import org.springframework.web.bind.annotation.*;

import com.example.aiExamAssistant.dto.GenerateQuestionRequest;
import com.example.aiExamAssistant.dto.GenerateQuestionResponse;
@RestController
@RequestMapping("/api/tutor")
public class AiTutorController {

    private final AiTutorService aiTutorService;

    public AiTutorController(AiTutorService aiTutorService) {
        this.aiTutorService = aiTutorService;
    }

    @PostMapping("/ask")
    public TutorResponse askQuestion(
            @RequestBody TutorQuestionRequest request) {

        return aiTutorService.askTutor(request);
    }
    
@PostMapping("/evaluate")
public TutorAnswerResponse evaluateAnswer(
        @RequestBody TutorAnswerRequest request) {
    return aiTutorService.evaluateAnswer(request);
}

@PostMapping("/generate-question")
public GenerateQuestionResponse generateQuestion(
        @RequestBody GenerateQuestionRequest request) {

    return aiTutorService.generateQuestion(request);
}

}