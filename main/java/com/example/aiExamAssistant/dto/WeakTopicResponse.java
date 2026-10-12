
package com.example.aiExamAssistant.dto;

public class WeakTopicResponse {

    private String subject;
    private String topic;
    private int totalAttempts;
    private int correctAttempts;
    private int incorrectAttempts;
    private double accuracy;
    private String status;

    public WeakTopicResponse() {
    }

    public WeakTopicResponse(
            String subject,
            String topic,
            int totalAttempts,
            int correctAttempts,
            int incorrectAttempts,
            double accuracy,
            String status) {

        this.subject = subject;
        this.topic = topic;
        this.totalAttempts = totalAttempts;
        this.correctAttempts = correctAttempts;
        this.incorrectAttempts = incorrectAttempts;
        this.accuracy = accuracy;
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public int getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public int getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public void setIncorrectAttempts(int incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
