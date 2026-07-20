package com.aiplanner.aiprojectplanner.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final ChatClient chatClient;

    public AIController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/test")
    public String testAI() {

        return chatClient
                .prompt("Explain what an SDLC project planner does")
                .call()
                .content();
    }
}