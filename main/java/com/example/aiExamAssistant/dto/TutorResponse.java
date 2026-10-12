package com.example.aiExamAssistant.dto;

public class TutorResponse {

    private String explanation;
    private String followUpQuestion;

    public TutorResponse() {
    }

    public TutorResponse(
            String explanation,
            String followUpQuestion) {
        this.explanation = explanation;
        this.followUpQuestion = followUpQuestion;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getFollowUpQuestion() {
        return followUpQuestion;
    }

    public void setFollowUpQuestion(String followUpQuestion) {
        this.followUpQuestion = followUpQuestion;
    }
}