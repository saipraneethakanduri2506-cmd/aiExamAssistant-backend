package com.example.aiExamAssistant.service;

import com.example.aiExamAssistant.dto.RegistrationRequest;
import com.example.aiExamAssistant.model.Exam;
import com.example.aiExamAssistant.model.Stream;
import com.example.aiExamAssistant.model.User;

import com.example.aiExamAssistant.repository.ExamRepository;
import com.example.aiExamAssistant.repository.StreamRepository;
import com.example.aiExamAssistant.repository.UserRepository;
import com.example.aiExamAssistant.dto.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StreamRepository streamRepository;
    private final ExamRepository examRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            StreamRepository streamRepository,
            ExamRepository examRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.streamRepository = streamRepository;
        this.examRepository = examRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegistrationRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists.");
        }

        String mobile = request.getMobile();

        if (mobile != null && !mobile.isBlank()) {
            mobile = mobile.trim();

            if (userRepository.existsByMobile(mobile)) {
                throw new IllegalArgumentException(
                        "An account with this mobile number already exists.");
            }
        } else {
            mobile = null;
        }

        Stream stream = streamRepository
                .findById(request.getStreamId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Selected Intermediate stream does not exist."));

        Exam exam = examRepository
                .findById(request.getExamId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Selected competitive exam does not exist."));

        if (!exam.getStream().getId().equals(stream.getId())) {
            throw new IllegalArgumentException(
                    "The selected exam does not belong to the selected stream.");
        }

        User user = new User();

        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setMobile(mobile);

        // Store the BCrypt hash, never the raw password.
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        user.setStream(stream);
        user.setExam(exam);

        user.setIntermediateYear(request.getIntermediateYear());
        user.setTargetExamYear(request.getTargetExamYear());
        user.setDailyStudyMinutes(request.getDailyStudyMinutes());
        user.setGoal(request.getGoal());

        // Never allow public registration to choose an admin role.
        user.setRole("STUDENT");

        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean mobileExists(String mobile) {
        return userRepository.existsByMobile(mobile);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));
    }
    public User login(LoginRequest request) {

    String email = request.getEmail()
            .trim()
            .toLowerCase(Locale.ROOT);

    User user = userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Invalid email or password."));

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        throw new IllegalArgumentException(
                "Invalid email or password.");
    }

    return user;
}
}