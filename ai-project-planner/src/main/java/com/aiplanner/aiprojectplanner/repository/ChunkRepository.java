package com.aiplanner.aiprojectplanner.repository;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChunkRepository extends JpaRepository<DocumentChunk, Long> {


    @Query(
            value = """
        SELECT *
        FROM document_chunks
        ORDER BY embedding <-> CAST(:embedding AS vector)
        LIMIT CAST(:limit AS INTEGER)
        """,
            nativeQuery = true
    )
    List<DocumentChunk> findSimilarChunks(
            @Param("embedding") String embedding,
            @Param("limit") int limit
    );

}