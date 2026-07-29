package com.aiplanner.aiprojectplanner.rag;

import lombok.Data;

@Data
public class Retrieval {

    private String chunkText;

    private Double similarityScore;

}