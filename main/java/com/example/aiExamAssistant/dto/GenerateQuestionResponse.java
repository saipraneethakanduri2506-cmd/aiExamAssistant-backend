
package com.example.aiExamAssistant.dto;

public class GenerateQuestionResponse {

    private String question;
    private String expectedAnswer;
    private String explanation;
    private String difficulty;

    public GenerateQuestionResponse() {
    }

    public GenerateQuestionResponse(
            String question,
            String expectedAnswer,
            String explanation,
            String difficulty) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
        this.explanation = explanation;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
