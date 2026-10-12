package com.example.aiExamAssistant.controller;

import com.example.aiExamAssistant.model.Stream;
import com.example.aiExamAssistant.repository.StreamRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
@CrossOrigin(origins = "http://localhost:5173")
public class StreamController {

    private final StreamRepository streamRepository;

    public StreamController(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    @GetMapping
    public List<Stream> getAllStreams() {
        return streamRepository.findAll();
    }
}