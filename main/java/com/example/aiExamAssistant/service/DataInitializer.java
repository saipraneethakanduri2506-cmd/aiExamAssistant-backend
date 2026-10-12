package com.example.aiExamAssistant.service;

import com.example.aiExamAssistant.model.Stream;
import com.example.aiExamAssistant.model.Exam;

import com.example.aiExamAssistant.repository.StreamRepository;
import com.example.aiExamAssistant.repository.ExamRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            StreamRepository streamRepository,
            ExamRepository examRepository) {

        return args -> {

            // Initialize Intermediate Streams
            addStream(streamRepository, "MPC");
            addStream(streamRepository, "BiPC");
            addStream(streamRepository, "MEC");
            addStream(streamRepository, "CEC");

            System.out.println("Intermediate streams initialized successfully!");

            // Initialize Competitive Exams
            addExam(streamRepository, examRepository,
                    "JEE Main", "JEE_MAIN", "MPC");

            addExam(streamRepository, examRepository,
                    "JEE Advanced", "JEE_ADVANCED", "MPC");

            addExam(streamRepository, examRepository,
                    "EAPCET Engineering", "EAPCET_ENGINEERING", "MPC");

            addExam(streamRepository, examRepository,
                    "NEET UG", "NEET_UG", "BiPC");

            addExam(streamRepository, examRepository,
                    "EAPCET Agriculture and Pharmacy",
                    "EAPCET_AGRI_PHARMACY", "BiPC");

            System.out.println("Competitive exams initialized successfully!");
        };
    }

    // Add Intermediate Stream
    private void addStream(
            StreamRepository repository,
            String name) {

        if (repository.findByNameIgnoreCase(name).isEmpty()) {
            repository.save(new Stream(name));
        }
    }

    // Add Competitive Exam
    private void addExam(
            StreamRepository streamRepository,
            ExamRepository examRepository,
            String name,
            String code,
            String streamName) {

        if (examRepository.findByCodeIgnoreCase(code).isEmpty()) {

            Stream stream = streamRepository
                    .findByNameIgnoreCase(streamName)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Stream not found: " + streamName));

            Exam exam = new Exam(name, code, stream);

            examRepository.save(exam);
        }
    }
}
