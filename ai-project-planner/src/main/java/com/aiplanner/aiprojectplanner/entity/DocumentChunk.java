package com.aiplanner.aiprojectplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_chunks")
@Data
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "document_id")
    private Long documentId;


    @Column(name = "chunk_text", columnDefinition = "TEXT")
    private String chunkText;


    /*
       PostgreSQL pgvector column

       Temporary mapping.
       Later we can use Hibernate Vector support.
    */
    @Column(columnDefinition = "vector(768)")
    private String embedding;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}