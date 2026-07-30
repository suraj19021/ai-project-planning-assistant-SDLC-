package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.repository.ChunkRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetrievalServiceImpl implements RetrievalService {


    private final EmbeddingService embeddingService;

    private final ChunkRepository chunkRepository;


    public RetrievalServiceImpl(
            EmbeddingService embeddingService,
            ChunkRepository chunkRepository
    ) {
        this.embeddingService = embeddingService;
        this.chunkRepository = chunkRepository;
    }


    @Override
    public List<DocumentChunk> retrieveRelevantChunks(
            String query,
            int limit
    ) {


        // Validate user query
        if (query == null || query.isBlank()) {

            System.out.println(
                    "Empty query received. Returning no chunks."
            );

            return Collections.emptyList();
        }


        // Validate limit
        if (limit <= 0) {

            limit = 5;
        }


        System.out.println(
                "Generating embedding for query..."
        );


        // Generate embedding using Ollama embedding model
        List<Double> embedding =
                embeddingService.generateEmbedding(query);



        if (embedding == null || embedding.isEmpty()) {

            System.out.println(
                    "Embedding generation failed."
            );

            return Collections.emptyList();
        }



        // Convert embedding list to pgvector format
        String vector =
                convertToVector(embedding);



        System.out.println(
                "Searching similar chunks using pgvector..."
        );



        // Retrieve nearest chunks from PostgreSQL
        List<DocumentChunk> chunks =
                chunkRepository.findSimilarChunks(
                        vector,
                        limit
                );



        System.out.println(
                "Retrieved chunks count : "
                        + chunks.size()
        );


        return chunks;
    }



    /**
     * Converts List<Double> into PostgreSQL vector format
     *
     * Example:
     *
     * [0.12,0.45,0.78]
     *
     */
    private String convertToVector(
            List<Double> embedding
    ) {


        return embedding.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));

    }

}