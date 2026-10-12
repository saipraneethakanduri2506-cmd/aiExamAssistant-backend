package com.example.aiExamAssistant.repository;

import com.example.aiExamAssistant.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByExam_Id(Long examId);

    Optional<Subject> findByNameIgnoreCaseAndExam_Id(
            String name,
            Long examId
    );
}