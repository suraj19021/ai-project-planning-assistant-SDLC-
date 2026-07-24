package com.aiplanner.aiprojectplanner.ai;

import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.enums.ProjectStatus;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    private final ChatClient chatClient;

    public AIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public ProjectResponseDTO analyzeProject(String documentText) {

        System.out.println("STEP 5 - Building prompt");

        String prompt = PromptBuilder.buildExecutiveSummaryPrompt(documentText);

        System.out.println("STEP 6 - Calling AI...");

        String aiResponse = chatClient
                .prompt(prompt)
                .call()
                .content();
        System.out.println("STEP 7 - AI returned");


        return ProjectResponseDTO.builder()
                .projectName("AI Generated Project")
                .executiveSummary(aiResponse)
                .projectObjective("")
                .timeline("")
                .resourceRecommendation("")
                .technologyStack("")
                .riskAnalysis("")
                .status(ProjectStatus.ANALYZED)
                .build();
    }
}