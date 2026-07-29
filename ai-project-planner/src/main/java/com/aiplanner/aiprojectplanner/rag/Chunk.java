package com.aiplanner.aiprojectplanner.rag;

import lombok.Data;

@Data
public class Chunk {

    private Long id;

    private Long documentId;

    private String text;

}