package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.repository.ChunkRepository;
import org.springframework.stereotype.Service;

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

        // 1. Convert query text into embedding vector
        List<Double> embedding =
                embeddingService.generateEmbedding(query);


        // 2. Convert List<Double> to pgvector format
        String vector = embedding.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));


        // 3. Search similar chunks from PostgreSQL pgvector
        return chunkRepository.findSimilarChunks(
                vector,
                limit
        );
    }
}