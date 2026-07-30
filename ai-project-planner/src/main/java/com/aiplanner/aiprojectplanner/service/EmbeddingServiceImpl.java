package com.aiplanner.aiprojectplanner.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingServiceImpl(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public List<Double> generateEmbedding(String text) {

        // Validate input
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be null or empty.");
        }

        // Generate embedding using Ollama model (nomic-embed-text)
        float[] embedding = embeddingModel.embed(text);

        // Convert float[] to List<Double>
        return IntStream
                .range(0, embedding.length)
                .mapToObj(i -> (double) embedding[i])
                .toList();
    }
}