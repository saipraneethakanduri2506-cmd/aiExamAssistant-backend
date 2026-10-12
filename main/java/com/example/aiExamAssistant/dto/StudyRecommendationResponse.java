
package com.example.aiExamAssistant.dto;

public class StudyRecommendationResponse {

    private String subject;
    private String topic;
    private String priority;
    private String recommendation;
    private double accuracy;

    public StudyRecommendationResponse() {
    }

    public StudyRecommendationResponse(
            String subject,
            String topic,
            String priority,
            String recommendation,
            double accuracy) {
        this.subject = subject;
        this.topic = topic;
        this.priority = priority;
        this.recommendation = recommendation;
        this.accuracy = accuracy;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
