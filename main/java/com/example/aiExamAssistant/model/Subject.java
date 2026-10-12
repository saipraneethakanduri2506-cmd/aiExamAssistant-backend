package com.example.aiExamAssistant.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "subjects",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_subject_name_exam",
            columnNames = {"name", "exam_id"}
        )
    }
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    public Subject() {
    }

    public Subject(String name, Exam exam) {
        this.name = name;
        this.exam = exam;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}