
package com.example.aiExamAssistant.repository;

import com.example.aiExamAssistant.model.TopicPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicPerformanceRepository
        extends JpaRepository<TopicPerformance, Long> {

    Optional<TopicPerformance> findByStudentIdAndSubjectAndTopic(
            Long studentId,
            String subject,
            String topic
    );

    List<TopicPerformance> findByStudentId(Long studentId);
}
