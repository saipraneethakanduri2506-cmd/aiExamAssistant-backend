
package com.example.aiExamAssistant.service;

import com.example.aiExamAssistant.dto.StudyRecommendationResponse;
import com.example.aiExamAssistant.model.TopicPerformance;
import com.example.aiExamAssistant.repository.TopicPerformanceRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StudyRecommendationService {

    private final TopicPerformanceRepository performanceRepository;

    public StudyRecommendationService(
            TopicPerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<StudyRecommendationResponse> getRecommendations(
            Long studentId) {

        List<TopicPerformance> records =
                performanceRepository.findByStudentId(studentId);

        List<StudyRecommendationResponse> recommendations =
                new ArrayList<>();

        for (TopicPerformance record : records) {

            int total = record.getTotalAttempts();
            int correct = record.getCorrectAttempts();

            double accuracy = total == 0
                    ? 0.0
                    : (correct * 100.0) / total;

            String priority;
            String recommendation;

            if (total < 3) {
                priority = "PRACTICE";
                recommendation =
                        "Attempt at least 3 questions on this topic "
                        + "to assess your understanding.";
            } else if (accuracy < 50.0) {
                priority = "HIGH";
                recommendation =
                        "Review the basic concepts, study a worked "
                        + "example, and practise 3–5 questions.";
            } else if (accuracy < 80.0) {
                priority = "MEDIUM";
                recommendation =
                        "Revise the concepts you answered incorrectly "
                        + "and practise more questions.";
            } else {
                priority = "LOW";
                recommendation =
                        "Your performance is strong. Maintain it "
                        + "with regular practice questions.";
            }

            recommendations.add(
                    new StudyRecommendationResponse(
                            record.getSubject(),
                            record.getTopic(),
                            priority,
                            recommendation,
                            Math.round(accuracy * 100.0) / 100.0
                    )
            );
        }

        recommendations.sort(
                Comparator.comparingInt(
                        item -> getPriorityOrder(item.getPriority())
                )
        );

        return recommendations;
    }

    private int getPriorityOrder(String priority) {
        switch (priority) {
            case "HIGH":
                return 1;
            case "MEDIUM":
                return 2;
            case "PRACTICE":
                return 3;
            default:
                return 4;
        }
    }
}
