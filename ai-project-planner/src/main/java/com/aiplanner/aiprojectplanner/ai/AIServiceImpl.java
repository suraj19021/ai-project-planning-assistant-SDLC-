package com.aiplanner.aiprojectplanner.ai;

import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.enums.ProjectStatus;
import com.aiplanner.aiprojectplanner.service.RetrievalService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIServiceImpl implements AIService {


    private final ChatClient chatClient;

    private final RetrievalService retrievalService;


    public AIServiceImpl(
            ChatClient.Builder chatClientBuilder,
            RetrievalService retrievalService
    ) {

        this.chatClient = chatClientBuilder.build();
        this.retrievalService = retrievalService;
    }



    @Override
    public String askQuestion(String question) {


        System.out.println(
                "STEP 1 - Retrieving relevant document chunks"
        );


        List<DocumentChunk> chunks =
                retrievalService.retrieveRelevantChunks(
                        question,
                        5
                );


        System.out.println(
                "STEP 2 - Retrieved chunks : "
                        + chunks.size()
        );



        StringBuilder context = new StringBuilder();



        for(DocumentChunk chunk : chunks) {

            context.append(
                    chunk.getChunkText()
            ).append("\n\n");

        }



        String prompt = """
                
                You are an AI Project Planning Assistant.

                Answer the user question using only the provided context.

                If the answer is not available in the context,
                reply:
                "I could not find the answer in the uploaded documents."


                -------- CONTEXT --------

                %s


                -------- QUESTION --------

                %s


                -------- ANSWER --------

                """
                .formatted(
                        context,
                        question
                );



        System.out.println(
                "STEP 3 - Sending RAG prompt to Ollama"
        );



        String response =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();



        System.out.println(
                "STEP 4 - Response generated"
        );



        return response;
    }





    @Override
    public ProjectResponseDTO analyzeProject(
            String documentText
    ) {


        String prompt =
                PromptBuilder
                        .buildExecutiveSummaryPrompt(
                                documentText
                        );



        String aiResponse =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();



        return ProjectResponseDTO.builder()

                .projectName(
                        "AI Generated Project"
                )

                .executiveSummary(
                        aiResponse
                )

                .projectObjective("")
                .timeline("")
                .resourceRecommendation("")
                .technologyStack("")
                .riskAnalysis("")

                .status(
                        ProjectStatus.ANALYZED
                )

                .build();
    }

}