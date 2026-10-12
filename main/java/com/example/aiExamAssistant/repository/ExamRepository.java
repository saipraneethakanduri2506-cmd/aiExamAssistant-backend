package com.example.aiExamAssistant.repository;

import com.example.aiExamAssistant.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByStream_Id(Long streamId);

    Optional<Exam> findByCodeIgnoreCase(String code);
}