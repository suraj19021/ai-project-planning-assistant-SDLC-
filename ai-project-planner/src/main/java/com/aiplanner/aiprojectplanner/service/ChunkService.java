package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;

import java.util.List;

public interface ChunkService {


    List<DocumentChunk> saveChunks(
            List<DocumentChunk> chunks
    );


    List<DocumentChunk> getAllChunks();


    void processDocument(
            Long documentId,
            String documentText
    );

}