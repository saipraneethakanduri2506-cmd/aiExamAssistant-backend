package com.example.aiExamAssistant.repository;

import com.example.aiExamAssistant.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findByNameIgnoreCase(String name);
}