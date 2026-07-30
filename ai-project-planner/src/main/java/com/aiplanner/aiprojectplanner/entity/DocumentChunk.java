package com.aiplanner.aiprojectplanner.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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


    @Column(
            name = "chunk_text",
            columnDefinition = "TEXT"
    )
    private String chunkText;


    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    @Column(
            name = "embedding",
            columnDefinition = "vector(768)"
    )
    private float[] embedding;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate(){

        createdAt = LocalDateTime.now();

    }

}