package com.example.aiExamAssistant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @Size(max = 15, message = "Mobile number cannot exceed 15 characters")
    private String mobile;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must contain 8 to 72 characters")
    private String password;

    @NotNull(message = "Please select your Intermediate stream")
    private Long streamId;

    @NotNull(message = "Please select your competitive exam")
    private Long examId;

    @Min(value = 2000, message = "Enter a valid Intermediate passing year")
    @Max(value = 2100, message = "Enter a valid Intermediate passing year")
    private Integer intermediateYear;

    @Min(value = 2026, message = "Enter a valid target exam year")
    @Max(value = 2100, message = "Enter a valid target exam year")
    private Integer targetExamYear;

    @Min(value = 15, message = "Daily study time must be at least 15 minutes")
    @Max(value = 1440, message = "Daily study time cannot exceed 1440 minutes")
    private Integer dailyStudyMinutes;

    @Size(max = 500, message = "Goal cannot exceed 500 characters")
    private String goal;

    public RegistrationRequest() {
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Integer getIntermediateYear() {
        return intermediateYear;
    }

    public void setIntermediateYear(Integer intermediateYear) {
        this.intermediateYear = intermediateYear;
    }

    public Integer getTargetExamYear() {
        return targetExamYear;
    }

    public void setTargetExamYear(Integer targetExamYear) {
        this.targetExamYear = targetExamYear;
    }

    public Integer getDailyStudyMinutes() {
        return dailyStudyMinutes;
    }

    public void setDailyStudyMinutes(Integer dailyStudyMinutes) {
        this.dailyStudyMinutes = dailyStudyMinutes;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}