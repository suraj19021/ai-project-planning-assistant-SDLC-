package com.aiplanner.aiprojectplanner.repository;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChunkRepository extends JpaRepository<DocumentChunk, Long> {

    /*
     * 1. GENERAL SIMILARITY SEARCH ACROSS ALL DOCUMENTS
     * Uses pgvector cosine distance operator (<=>)
     */
    @Query(
            value = """
                    SELECT *
                    FROM document_chunks
                    ORDER BY embedding <=> CAST(:embedding AS vector)
                    LIMIT CAST(:limit AS INTEGER)
                    """,
            nativeQuery = true
    )
    List<DocumentChunk> findSimilarChunks(
            @Param("embedding") String embedding,
            @Param("limit") int limit
    );

    /*
     * 2. DOCUMENT-FILTERED SIMILARITY SEARCH
     * Restricts vector search strictly to a single document ID
     */
    @Query(
            value = """
                    SELECT *
                    FROM document_chunks
                    WHERE document_id = :documentId
                    ORDER BY embedding <=> CAST(:embedding AS vector)
                    LIMIT CAST(:limit AS INTEGER)
                    """,
            nativeQuery = true
    )
    List<DocumentChunk> findSimilarChunksByDocumentId(
            @Param("embedding") String embedding,
            @Param("documentId") Long documentId,
            @Param("limit") int limit
    );

    /*
     * 3. FETCH ALL ACTIVE DISTINCT DOCUMENT IDs
     * Used by AIService to iterate through documents dynamically
     */
    @Query(
            value = "SELECT DISTINCT document_id FROM document_chunks",
            nativeQuery = true
    )
    List<Long> findDistinctDocumentIds();
}