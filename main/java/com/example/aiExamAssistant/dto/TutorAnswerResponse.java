
package com.example.aiExamAssistant.dto;

public class TutorAnswerResponse {

    private boolean correct;
    private String feedback;
    private String correctAnswer;
    private String explanation;
    private boolean needsMorePractice;

    public TutorAnswerResponse() {
    }

    public TutorAnswerResponse(
            boolean correct,
            String feedback,
            String correctAnswer,
            String explanation,
            boolean needsMorePractice) {
        this.correct = correct;
        this.feedback = feedback;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.needsMorePractice = needsMorePractice;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isNeedsMorePractice() {
        return needsMorePractice;
    }

    public void setNeedsMorePractice(boolean needsMorePractice) {
        this.needsMorePractice = needsMorePractice;
    }
}
