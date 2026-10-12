package com.example.aiExamAssistant.dto;

public class StudentProfileResponse {

    private Long id;
    private String fullName;
    private String email;
    private String mobile;
    private String stream;
    private String exam;
    private Integer intermediateYear;
    private Integer targetExamYear;
    private Integer dailyStudyMinutes;
    private String goal;

    public StudentProfileResponse() {
    }

    public StudentProfileResponse(
            Long id,
            String fullName,
            String email,
            String mobile,
            String stream,
            String exam,
            Integer intermediateYear,
            Integer targetExamYear,
            Integer dailyStudyMinutes,
            String goal) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.stream = stream;
        this.exam = exam;
        this.intermediateYear = intermediateYear;
        this.targetExamYear = targetExamYear;
        this.dailyStudyMinutes = dailyStudyMinutes;
        this.goal = goal;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getStream() {
        return stream;
    }

    public String getExam() {
        return exam;
    }

    public Integer getIntermediateYear() {
        return intermediateYear;
    }

    public Integer getTargetExamYear() {
        return targetExamYear;
    }

    public Integer getDailyStudyMinutes() {
        return dailyStudyMinutes;
    }

    public String getGoal() {
        return goal;
    }
}