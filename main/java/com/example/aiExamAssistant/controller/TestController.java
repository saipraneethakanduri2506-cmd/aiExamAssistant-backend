package com.example.aiExamAssistant.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protected")
    public String protectedEndpoint(Authentication authentication) {

        System.out.println("TEST CONTROLLER REACHED");

        return "JWT authentication successful! Logged in as: "
                + authentication.getName();
    }
}