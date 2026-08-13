package com.aiplanner.aiprojectplanner.ai;

import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.enums.ProjectStatus;
import com.aiplanner.aiprojectplanner.service.RetrievalService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.*;

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

        System.out.println("==============================================");
        System.out.println("STEP 1 - Performing Sub-Query Decomposed Retrieval");
        System.out.println("Question: " + question);

        // Fetch all active document IDs from DB
        List<Long> activeDocIds = retrievalService.getAllActiveDocumentIds();

        // 1. GENERATE DECOMPOSED SUB-QUERIES FOR DEEP COVERAGE
        List<String> subQueries = List.of(
                question,                                                    // Original user query
                "programming languages technology stack software implementation",  // Tech stack focus
                "architecture system structure communication protocol design",      // Architecture focus
                "data persistence database storage model data structures"          // Persistence focus
        );

        Set<Long> seenChunkIds = new HashSet<>();
        List<DocumentChunk> finalBalancedChunks = new ArrayList<>();

        // 2. ITERATE OVER DOCUMENTS AND SUB-QUERIES
        for (Long docId : activeDocIds) {
            for (String subQuery : subQueries) {
                // Fetch top 2 chunks per sub-query for this specific document
                List<DocumentChunk> chunks = retrievalService.retrieveRelevantChunksByDocId(subQuery, docId, 2);

                for (DocumentChunk chunk : chunks) {
                    // Deduplicate chunks so we don't repeat text
                    if (seenChunkIds.add(chunk.getId())) {
                        finalBalancedChunks.add(chunk);
                    }
                }
            }
        }

        System.out.println("STEP 2 - Total Decomposed Chunks Retrieved: " + finalBalancedChunks.size());

        // Show document breakdown in console
        Set<Long> retrievedDocIds = new LinkedHashSet<>();
        for (DocumentChunk chunk : finalBalancedChunks) {
            retrievedDocIds.add(chunk.getDocumentId());
        }
        System.out.println("Retrieved Document IDs: " + retrievedDocIds);

        /*
         * 3. BUILD GROUNDED CONTEXT
         */
        // Inside AIServiceImpl.java
        StringBuilder context = new StringBuilder();

        for (DocumentChunk chunk : finalBalancedChunks) {
            // Pass the actual file name or a label instead of raw DB ID
            String displayLabel = chunk.getFileName() != null ? chunk.getFileName() : "Document " + chunk.getDocumentId();

            context.append("=== DOCUMENT: ").append(displayLabel).append(" ===\n");
            context.append(chunk.getChunkText());
            context.append("\n\n");
        }

        /*
         * 4. GROUNDED RAG PROMPT
         */
        String prompt = """
        You are an AI Project Planning Assistant.

        You must answer the user's question using ONLY the information contained in the provided documents.

        IMPORTANT RULES:
        1. Do NOT use your own general knowledge or invent details.
        2. Do NOT infer that two systems are similar or compatible unless explicitly stated.
        3. For multi-part or comparison questions, compare the documents attribute by attribute based on available context.
        4. If a specific detail (e.g., persistence model or architecture) is missing for one of the documents, explicitly state:
           "Not specified in the provided context for Document ID: [ID]"
        5. ONLY if NO RELEVANT INFORMATION is found in ANY uploaded document, reply exactly:
           "I could not find the answer in the uploaded documents."
        6. Clearly attribute all findings to their respective DOCUMENT ID / File Name.

        -------- RETRIEVED DOCUMENT CONTEXT --------
        %s

        -------- USER QUESTION --------
        %s

        -------- ANSWER --------
        """.formatted(context.toString(), question);

        System.out.println("STEP 3 - Sending Grounded Prompt to Ollama");

        String response = chatClient.prompt(prompt).call().content();

        System.out.println("STEP 4 - Response Generated");
        System.out.println("==============================================");

        return response;
    }

    @Override
    public ProjectResponseDTO analyzeProject(String documentText) {
        String prompt = PromptBuilder.buildExecutiveSummaryPrompt(documentText);
        String aiResponse = chatClient.prompt(prompt).call().content();

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