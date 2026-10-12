package com.example.aiExamAssistant.controller;
import com.example.aiExamAssistant.dto.RegistrationRequest;
import com.example.aiExamAssistant.dto.RegistrationResponse;
import com.example.aiExamAssistant.model.User;
import com.example.aiExamAssistant.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.aiExamAssistant.dto.LoginRequest;
import com.example.aiExamAssistant.dto.LoginResponse;
import com.example.aiExamAssistant.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
private final JwtService jwtService;

public AuthController(
        AuthService authService,
        JwtService jwtService) {

    this.authService = authService;
    this.jwtService = jwtService;
}
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(
            @Valid @RequestBody RegistrationRequest request) {

        User user = authService.register(request);

        RegistrationResponse response = new RegistrationResponse(
                "Registration successful!",
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getStream().getName(),
                user.getExam().getName()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/login")
public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request) {

    var user = authService.login(request);

    String token = jwtService.generateToken(
            user.getEmail());

    LoginResponse response = new LoginResponse(
            "Login successful!",
            token,
            3600,
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getStream().getName(),
            user.getExam().getName(),
            user.getRole()
    );

    return ResponseEntity.ok(response);
}
}