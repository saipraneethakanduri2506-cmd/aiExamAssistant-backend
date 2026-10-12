package com.example.aiExamAssistant.repository;

import com.example.aiExamAssistant.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findBySubject_Id(Long subjectId);

    Optional<Topic> findByNameIgnoreCaseAndSubject_Id(
            String name,
            Long subjectId
    );
}