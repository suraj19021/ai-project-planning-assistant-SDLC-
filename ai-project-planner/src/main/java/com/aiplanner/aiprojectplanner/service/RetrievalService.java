package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;

import java.util.List;

public interface RetrievalService {

    List<DocumentChunk> retrieveRelevantChunks(
            String query,
            int limit
    );

}