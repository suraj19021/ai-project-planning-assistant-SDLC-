package com.aiplanner.aiprojectplanner.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    private final ChatClient chatClient;

    public AIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generateExecutiveSummary(String documentText) {

        String prompt = PromptBuilder.buildExecutiveSummaryPrompt(documentText);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}