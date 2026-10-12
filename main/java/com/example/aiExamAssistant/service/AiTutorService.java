package com.example.aiExamAssistant.service;
import com.example.aiExamAssistant.dto.TutorAnswerRequest;
import com.example.aiExamAssistant.dto.TutorAnswerResponse;
import com.example.aiExamAssistant.dto.TutorQuestionRequest;
import com.example.aiExamAssistant.dto.TutorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.aiExamAssistant.model.User;
import com.example.aiExamAssistant.model.TopicPerformance;
import com.example.aiExamAssistant.repository.UserRepository;
import com.example.aiExamAssistant.repository.TopicPerformanceRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.aiExamAssistant.dto.GenerateQuestionRequest;
import com.example.aiExamAssistant.dto.GenerateQuestionResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class AiTutorService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.model}")
    private String model;


private final ObjectMapper objectMapper;
private final HttpClient httpClient;
private final UserRepository userRepository;
private final TopicPerformanceRepository topicPerformanceRepository;


    
public AiTutorService(
        ObjectMapper objectMapper,
        UserRepository userRepository,
        TopicPerformanceRepository topicPerformanceRepository) {

    this.objectMapper = objectMapper;
    this.userRepository = userRepository;
    this.topicPerformanceRepository = topicPerformanceRepository;

    this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();
}


    public TutorResponse askTutor(TutorQuestionRequest request) {

        if (request.getQuestion() == null
                || request.getQuestion().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Question cannot be empty"
            );
        }

        String prompt = buildPrompt(request);

        try {
            ObjectNode body = objectMapper.createObjectNode();

            ObjectNode part = objectMapper.createObjectNode();
            part.put("text", prompt);

            ObjectNode content = objectMapper.createObjectNode();
            content.putArray("parts").add(part);

            body.putArray("contents").add(content);

            String endpoint =
                    "https://generativelanguage.googleapis.com/v1beta/models/"
                    + model + ":generateContent";

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .header("x-goog-api-key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200
                    || response.statusCode() >= 300) {

                System.err.println(
                        "Gemini API returned HTTP "
                        + response.statusCode()
                );

                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "The AI tutor could not process your request"
                );
            }

            JsonNode result =
                    objectMapper.readTree(response.body());

            String generatedText = result
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText("");

            if (generatedText.isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "The AI tutor returned an empty response"
                );
            }

            return parseTutorResponse(generatedText);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Could not read the AI tutor response"
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "The AI tutor request was interrupted"
            );
        }
    }

    private String buildPrompt(TutorQuestionRequest request) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an AI Study Assistant for students
                preparing for competitive examinations after
                completing Intermediate education.

                Explain concepts accurately and clearly.
                Use simple language, step-by-step reasoning,
                and an example where useful.
                Match the explanation to the student's level.

                At the end, ask exactly one short question
                to check the student's understanding.

                Format your response exactly as follows:

                EXPLANATION:
                Write the teaching explanation here.

                FOLLOW_UP_QUESTION:
                Write one question here.

                Do not omit either heading.
                """);

        appendContext(prompt, "Stream", request.getStream());
        appendContext(prompt, "Exam", request.getExamType());
        appendContext(prompt, "Subject", request.getSubject());
        appendContext(prompt, "Topic", request.getTopic());

        prompt.append("\nSTUDENT QUESTION:\n");
        prompt.append(request.getQuestion());

        return prompt.toString();
    }

    private void appendContext(
            StringBuilder prompt,
            String label,
            String value) {

        if (value != null && !value.isBlank()) {
            prompt.append("\n")
                    .append(label)
                    .append(": ")
                    .append(value);
        }
    }

    private TutorResponse parseTutorResponse(String text) {

        String explanationHeading = "EXPLANATION:";
        String questionHeading = "FOLLOW_UP_QUESTION:";

        int explanationStart =
                text.indexOf(explanationHeading);

        int questionStart =
                text.indexOf(questionHeading);

        if (explanationStart >= 0
                && questionStart > explanationStart) {

            String explanation = text.substring(
                    explanationStart + explanationHeading.length(),
                    questionStart
            ).trim();

            String followUpQuestion = text.substring(
                    questionStart + questionHeading.length()
            ).trim();

            if (!explanation.isBlank()
                    && !followUpQuestion.isBlank()) {
                return new TutorResponse(
                        explanation,
                        followUpQuestion
                );
            }
        }

        // Fallback if Gemini does not follow the requested format.
        return new TutorResponse(
                text.trim(),
                "Can you explain the main idea in your own words?"
        );
    }
    
public TutorAnswerResponse evaluateAnswer(TutorAnswerRequest request) {

    if (request.getQuestion() == null
            || request.getQuestion().isBlank()
            || request.getStudentAnswer() == null
            || request.getStudentAnswer().isBlank()) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Question and student answer cannot be empty"
        );
    }

    String prompt = """
            You are an AI tutor evaluating a student's answer.

            Evaluate the answer fairly based on its meaning,
            not just exact wording. Accept equivalent correct
            explanations. Do not mark an answer correct if its
            main concept is incorrect.

            Explain mistakes in simple language suitable for
            an Intermediate-level student preparing for exams.

            Return your response in exactly this format:

            CORRECT: true or false
            FEEDBACK: brief feedback for the student
            CORRECT_ANSWER: the expected correct answer
            EXPLANATION: a clearer explanation of the concept
            NEEDS_MORE_PRACTICE: true or false

            Set NEEDS_MORE_PRACTICE to false if the answer
            demonstrates understanding. Set it to true if the
            student needs further practice.

            Subject: %s
            Topic: %s
            Stream: %s
            Exam: %s

            FOLLOW-UP QUESTION:
            %s

            STUDENT ANSWER:
            %s
            """.formatted(
                    safeValue(request.getSubject()),
                    safeValue(request.getTopic()),
                    safeValue(request.getStream()),
                    safeValue(request.getExamType()),
                    request.getQuestion(),
                    request.getStudentAnswer()
            );

    try {
        ObjectNode body = objectMapper.createObjectNode();

        ObjectNode part = objectMapper.createObjectNode();
        part.put("text", prompt);

        ObjectNode content = objectMapper.createObjectNode();
        content.putArray("parts").add(part);

        body.putArray("contents").add(content);

        String endpoint =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                + model + ":generateContent";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = httpClient.send(
                httpRequest,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {
            System.err.println(
                    "Gemini answer evaluation returned HTTP "
                    + response.statusCode()
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Could not evaluate the student answer"
            );
        }

        JsonNode result = objectMapper.readTree(response.body());

        String generatedText = result
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText("");

        if (generatedText.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "AI returned an empty evaluation"
            );
        }

        String correctValue =
                extractValue(generatedText, "CORRECT:");

        String feedback =
                extractSection(generatedText, "FEEDBACK:",
                        "CORRECT_ANSWER:");

        String correctAnswer =
                extractSection(generatedText, "CORRECT_ANSWER:",
                        "EXPLANATION:");

        String explanation =
                extractSection(generatedText, "EXPLANATION:",
                        "NEEDS_MORE_PRACTICE:");

        String practiceValue =
                extractValue(generatedText,
                        "NEEDS_MORE_PRACTICE:");

        if ((!correctValue.equalsIgnoreCase("true")
                && !correctValue.equalsIgnoreCase("false"))
                || feedback.isBlank()
                || correctAnswer.isBlank()
                || explanation.isBlank()
                || (!practiceValue.equalsIgnoreCase("true")
                && !practiceValue.equalsIgnoreCase("false"))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "AI returned an invalid evaluation format"
            );
        }

        
String email = SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

User student = userRepository.findByEmailIgnoreCase(email)
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Authenticated student was not found"
        ));

String subject = safeValue(request.getSubject());
String topic = safeValue(request.getTopic());

TopicPerformance performance =
        topicPerformanceRepository
                .findByStudentIdAndSubjectAndTopic(
                        student.getId(),
                        subject,
                        topic
                )
                .orElseGet(() -> {
                    TopicPerformance newPerformance =
                            new TopicPerformance();

                    newPerformance.setStudentId(student.getId());
                    newPerformance.setSubject(subject);
                    newPerformance.setTopic(topic);

                    return newPerformance;
                });

boolean isCorrect = Boolean.parseBoolean(correctValue);

performance.setTotalAttempts(
        performance.getTotalAttempts() + 1
);

if (isCorrect) {
    performance.setCorrectAttempts(
            performance.getCorrectAttempts() + 1
    );
} else {
    performance.setIncorrectAttempts(
            performance.getIncorrectAttempts() + 1
    );
}

topicPerformanceRepository.save(performance);

return new TutorAnswerResponse(
        isCorrect,
        feedback,
        correctAnswer,
        explanation,
        Boolean.parseBoolean(practiceValue)
);

 
    } catch (IOException e) {
        throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "Could not read the AI evaluation response"
        );
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "The AI evaluation request was interrupted"
        );
    }
}

private String safeValue(String value) {
    return value == null || value.isBlank()
            ? "Not specified"
            : value;
}

private String extractValue(String text, String heading) {
    int start = text.indexOf(heading);

    if (start < 0) {
        return "";
    }

    start += heading.length();

    int end = text.indexOf("\n", start);

    if (end < 0) {
        end = text.length();
    }

    return text.substring(start, end)
            .trim()
            .replace("*", "");
}

private String extractSection(
        String text,
        String heading,
        String nextHeading) {

    int start = text.indexOf(heading);

    if (start < 0) {
        return "";
    }

    start += heading.length();

    int end = text.indexOf(nextHeading, start);

    if (end < 0) {
        return "";
    }

    return text.substring(start, end).trim();
}

public GenerateQuestionResponse generateQuestion(
        GenerateQuestionRequest request) {

    if (request.getSubject() == null
            || request.getSubject().isBlank()
            || request.getTopic() == null
            || request.getTopic().isBlank()) {

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Subject and topic cannot be empty"
        );
    }

    String difficulty = request.getDifficulty();

    if (difficulty == null || difficulty.isBlank()) {
        difficulty = "Medium";
    }

    if (!difficulty.equalsIgnoreCase("Easy")
            && !difficulty.equalsIgnoreCase("Medium")
            && !difficulty.equalsIgnoreCase("Hard")) {

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Difficulty must be Easy, Medium, or Hard"
        );
    }

    String prompt = """
            You are an educational question generator for
            Intermediate-level students preparing for exams.

            Generate exactly one accurate practice question.
            Match its level to the requested difficulty.
            Keep it relevant to the selected subject and topic.

            Return exactly these four headings:

            QUESTION:
            Write the question.

            EXPECTED_ANSWER:
            Write the correct answer.

            EXPLANATION:
            Explain the answer clearly.

            DIFFICULTY:
            Write Easy, Medium, or Hard.

            Do not add any other headings.

            Stream: %s
            Exam: %s
            Subject: %s
            Topic: %s
            Difficulty: %s
            """.formatted(
                    safeValue(request.getStream()),
                    safeValue(request.getExamType()),
                    request.getSubject(),
                    request.getTopic(),
                    difficulty
            );

    try {
        ObjectNode body = objectMapper.createObjectNode();

        ObjectNode part = objectMapper.createObjectNode();
        part.put("text", prompt);

        ObjectNode content = objectMapper.createObjectNode();
        content.putArray("parts").add(part);

        body.putArray("contents").add(content);

        String endpoint =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                + model + ":generateContent";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = httpClient.send(
                httpRequest,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Gemini could not generate a question"
            );
        }

        JsonNode result = objectMapper.readTree(response.body());

        String generatedText = result
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText("");
System.out.println("Gemini generated question response:");
System.out.println(generatedText);
        String question = extractSection(
                generatedText, "QUESTION:", "EXPECTED_ANSWER:");

        String expectedAnswer = extractSection(
                generatedText, "EXPECTED_ANSWER:", "EXPLANATION:");

        String explanation = extractSection(
                generatedText, "EXPLANATION:", "DIFFICULTY:");

        String generatedDifficulty = extractValue(
                generatedText, "DIFFICULTY:");

        if (question.isBlank()
                || expectedAnswer.isBlank()
                || explanation.isBlank()
                || generatedDifficulty.isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Gemini returned an incomplete question"
            );
        }

        return new GenerateQuestionResponse(
                question,
                expectedAnswer,
                explanation,
                difficulty
        );

    } catch (IOException e) {
        throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "Could not read the generated question"
        );

    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Question generation was interrupted"
        );
    }
}



}