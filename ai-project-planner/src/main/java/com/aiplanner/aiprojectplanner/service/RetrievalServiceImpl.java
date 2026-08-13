package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.repository.ChunkRepository;
import org.springframework.stereotype.Service;

import java.util.*;
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

    /*
     * -------------------------------------------------------------
     * BALANCED MULTI-DOCUMENT RETRIEVAL
     * -------------------------------------------------------------
     */
    @Override
    public List<DocumentChunk> retrieveRelevantChunks(String query, int limitPerDoc) {

        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        if (limitPerDoc <= 0) {
            limitPerDoc = 3; // Default to 3 chunks per document
        }

        List<Double> embedding = embeddingService.generateEmbedding(query);
        if (embedding == null || embedding.isEmpty()) {
            return Collections.emptyList();
        }

        String vector = convertToVector(embedding);

        // Retrieve a larger candidate pool from pgvector
        int candidateLimit = 30;
        List<DocumentChunk> candidates = chunkRepository.findSimilarChunks(vector, candidateLimit);

        /*
         * STEP 1 - DEDUPLICATE PER DOCUMENT
         * Prevents identical chunks within the SAME document, but keeps
         * chunks if they belong to distinct Document IDs.
         */
        Set<String> seenDocAndText = new LinkedHashSet<>();
        List<DocumentChunk> uniqueCandidates = candidates.stream()
                .filter(chunk -> {
                    String docAndTextKey = chunk.getDocumentId() + ":" + normalizeText(chunk.getChunkText());
                    return seenDocAndText.add(docAndTextKey);
                })
                .collect(Collectors.toList());

        /*
         * STEP 2 - BALANCED ROUND-ROBIN SELECTION
         * Group candidates by Document ID and select equal chunks (limitPerDoc)
         * from every document represented in the pool.
         */
        Map<Long, List<DocumentChunk>> chunksByDoc = uniqueCandidates.stream()
                .collect(Collectors.groupingBy(
                        DocumentChunk::getDocumentId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<DocumentChunk> finalBalancedChunks = new ArrayList<>();

        for (Map.Entry<Long, List<DocumentChunk>> entry : chunksByDoc.entrySet()) {
            List<DocumentChunk> docChunks = entry.getValue();

            // Limit per document to guarantee equal representation
            List<DocumentChunk> selectedForDoc = docChunks.stream()
                    .limit(limitPerDoc)
                    .collect(Collectors.toList());

            finalBalancedChunks.addAll(selectedForDoc);
        }

        System.out.println("Final balanced chunks returned: " + finalBalancedChunks.size());
        System.out.println("Documents represented: " + chunksByDoc.keySet());

        return finalBalancedChunks;
    }

    /*
     * -------------------------------------------------------------
     * RETRIEVE CHUNKS FOR A SPECIFIC DOCUMENT ID
     * -------------------------------------------------------------
     */
    @Override
    public List<DocumentChunk> retrieveRelevantChunksByDocId(String query, Long documentId, int limit) {
        if (query == null || query.isBlank() || documentId == null) {
            return Collections.emptyList();
        }

        List<Double> embedding = embeddingService.generateEmbedding(query);
        if (embedding == null || embedding.isEmpty()) {
            return Collections.emptyList();
        }

        String vector = convertToVector(embedding);

        // INCREASE THIS FROM limit * 2 TO 20
        // Fetches top 20 candidate chunks for this document before filtering
        List<DocumentChunk> candidates = chunkRepository.findSimilarChunksByDocumentId(vector, documentId, 20);

        Set<String> seenText = new LinkedHashSet<>();
        return candidates.stream()
                .filter(chunk -> seenText.add(normalizeText(chunk.getChunkText())))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /*
     * -------------------------------------------------------------
     * FETCH ALL DISTINCT ACTIVE DOCUMENT IDs
     * -------------------------------------------------------------
     */
    @Override
    public List<Long> getAllActiveDocumentIds() {
        return chunkRepository.findDistinctDocumentIds();
    }

    private String normalizeText(String text) {
        if (text == null) return "";
        return text.replaceAll("\\s+", " ").trim().toLowerCase();
    }

    private String convertToVector(List<Double> embedding) {
        return embedding.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
    }
}