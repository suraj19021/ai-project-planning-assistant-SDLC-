package com.aiplanner.aiprojectplanner.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {


    private final EmbeddingModel embeddingModel;


    public EmbeddingServiceImpl(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }


    @Override
    public List<Double> generateEmbedding(String text) {

        float[] embedding = embeddingModel.embed(text);


        return java.util.stream.IntStream
                .range(0, embedding.length)
                .mapToObj(i -> (double) embedding[i])
                .toList();
    }
}