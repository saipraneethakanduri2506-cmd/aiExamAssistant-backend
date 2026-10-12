package com.example.aiExamAssistant.dto;

public class LoginResponse {

    private String message;
    private String token;
    private String tokenType;
    private long expiresInSeconds;

    private Long studentId;
    private String fullName;
    private String email;
    private String stream;
    private String exam;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            String token,
            long expiresInSeconds,
            Long studentId,
            String fullName,
            String email,
            String stream,
            String exam,
            String role) {

        this.message = message;
        this.token = token;
        this.tokenType = "Bearer";
        this.expiresInSeconds = expiresInSeconds;
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.stream = stream;
        this.exam = exam;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getStream() {
        return stream;
    }

    public String getExam() {
        return exam;
    }

    public String getRole() {
        return role;
    }
}