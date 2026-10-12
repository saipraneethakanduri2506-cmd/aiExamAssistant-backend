package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.model.Subject;
import com.example.aiExamAssistant.repository.SubjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:5173")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<Subject>> getSubjectsByExam(
            @PathVariable Long examId) {

        List<Subject> subjects =
                subjectRepository.findByExam_Id(examId);

        return ResponseEntity.ok(subjects);
    }
}