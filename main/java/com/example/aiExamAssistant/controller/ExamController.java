package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.model.Exam;
import com.example.aiExamAssistant.repository.ExamRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "http://localhost:5173")
public class ExamController {

    private final ExamRepository examRepository;

    public ExamController(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    // Get all competitive exams
    @GetMapping
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    // Get exams based on Intermediate stream
    @GetMapping("/stream/{streamId}")
    public List<Exam> getExamsByStream(
            @PathVariable Long streamId) {

        return examRepository.findByStream_Id(streamId);
    }
}