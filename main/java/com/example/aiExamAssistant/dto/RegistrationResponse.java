package com.example.aiExamAssistant.dto;

public class RegistrationResponse {

    private String message;
    private Long id;
    private String fullName;
    private String email;
    private String stream;
    private String exam;

    public RegistrationResponse() {
    }

    public RegistrationResponse(
            String message,
            Long id,
            String fullName,
            String email,
            String stream,
            String exam) {

        this.message = message;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.stream = stream;
        this.exam = exam;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }
}