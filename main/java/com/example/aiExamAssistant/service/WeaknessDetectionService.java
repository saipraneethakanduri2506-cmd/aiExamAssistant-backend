
package com.example.aiExamAssistant.service;

import com.example.aiExamAssistant.dto.WeakTopicResponse;
import com.example.aiExamAssistant.model.TopicPerformance;
import com.example.aiExamAssistant.repository.TopicPerformanceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class WeaknessDetectionService {

    private final TopicPerformanceRepository performanceRepository;

    public WeaknessDetectionService(
            TopicPerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<WeakTopicResponse> getStudentPerformance(
            Long studentId) {

        List<TopicPerformance> records =
                performanceRepository.findByStudentId(studentId);

        return records.stream()
                .map(this::convertToResponse)
                .sorted(Comparator.comparingDouble(
                        WeakTopicResponse::getAccuracy))
                .toList();
    }

    private WeakTopicResponse convertToResponse(
            TopicPerformance performance) {

        int total = performance.getTotalAttempts();
        int correct = performance.getCorrectAttempts();
        int incorrect = performance.getIncorrectAttempts();

        double accuracy = total == 0
                ? 0.0
                : (correct * 100.0) / total;

        String status;

        if (total < 3) {
            status = "INSUFFICIENT_DATA";
        } else if (accuracy < 50.0) {
            status = "NEEDS_IMPROVEMENT";
        } else if (accuracy < 80.0) {
            status = "DEVELOPING";
        } else {
            status = "STRONG";
        }

        return new WeakTopicResponse(
                performance.getSubject(),
                performance.getTopic(),
                total,
                correct,
                incorrect,
                Math.round(accuracy * 100.0) / 100.0,
                status
        );
    }
}
