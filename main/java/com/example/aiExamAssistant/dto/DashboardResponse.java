package com.example.aiExamAssistant.dto;

public class DashboardResponse {

    private String studentName;
    private String stream;
    private String exam;
    private Integer targetExamYear;
    private Integer dailyStudyMinutes;
    private String goal;
    private String message;

    public DashboardResponse() {
    }

    public DashboardResponse(
            String studentName,
            String stream,
            String exam,
            Integer targetExamYear,
            Integer dailyStudyMinutes,
            String goal,
            String message) {

        this.studentName = studentName;
        this.stream = stream;
        this.exam = exam;
        this.targetExamYear = targetExamYear;
        this.dailyStudyMinutes = dailyStudyMinutes;
        this.goal = goal;
        this.message = message;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStream() {
        return stream;
    }

    public String getExam() {
        return exam;
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

    public String getMessage() {
        return message;
    }
}