package com.aiplanner.aiprojectplanner.controller;

import com.aiplanner.aiprojectplanner.ai.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {


    private final AIService aiService;


    public AIController(
            AIService aiService
    ) {
        this.aiService = aiService;
    }



    @GetMapping("/test")
    public String testAI() {

        return aiService.askQuestion(
                "Explain what an SDLC project planner does"
        );
    }



    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(
            @RequestBody String question
    ) {


        String answer =
                aiService.askQuestion(question);


        return ResponseEntity.ok(answer);
    }

}