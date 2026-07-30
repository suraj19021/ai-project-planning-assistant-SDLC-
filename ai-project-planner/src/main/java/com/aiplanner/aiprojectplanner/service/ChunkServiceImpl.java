package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.repository.ChunkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChunkServiceImpl implements ChunkService {


    private static final int CHUNK_SIZE = 500;
    private static final int EMBEDDING_SIZE = 768;


    private final ChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;


    public ChunkServiceImpl(
            ChunkRepository chunkRepository,
            EmbeddingService embeddingService
    ) {
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
    }


    @Override
    public List<DocumentChunk> saveChunks(
            List<DocumentChunk> chunks
    ) {

        return chunkRepository.saveAll(chunks);
    }


    @Override
    public List<DocumentChunk> getAllChunks() {

        return chunkRepository.findAll();
    }


    @Override
    public void processDocument(
            Long documentId,
            String documentText
    ) {


        if (documentText == null || documentText.isBlank()) {
            return;
        }


        int start = 0;


        while (start < documentText.length()) {


            int end = Math.min(
                    start + CHUNK_SIZE,
                    documentText.length()
            );


            String chunk =
                    documentText.substring(start, end);



            // Generate embedding from Ollama/Spring AI
            List<Double> embedding =
                    embeddingService.generateEmbedding(chunk);


            System.out.println("Embedding class: " + embedding.getClass());
            System.out.println("Embedding size: " + embedding.size());
            System.out.println("First value: " + embedding.get(0));


            DocumentChunk documentChunk =
                    new DocumentChunk();



            documentChunk.setDocumentId(documentId);


            documentChunk.setChunkText(chunk);



            // IMPORTANT: Store embedding
            documentChunk.setEmbedding(
                    convertToFloatArray(embedding)
            );



            documentChunk.setCreatedAt(
                    LocalDateTime.now()
            );



            chunkRepository.save(documentChunk);

            System.out.println(
                    "Entity embedding type: "
                            + documentChunk.getEmbedding().getClass()
            );

            System.out.println(
                    "Entity embedding length: "
                            + documentChunk.getEmbedding().length
            );

            start = end;
        }

    }



    private float[] convertToFloatArray(
            List<Double> embedding
    ) {


        if (embedding.size() != EMBEDDING_SIZE) {

            throw new IllegalArgumentException(
                    "Embedding size mismatch. Expected: "
                            + EMBEDDING_SIZE
                            + " but received: "
                            + embedding.size()
            );
        }



        float[] vector =
                new float[embedding.size()];



        for (int i = 0; i < embedding.size(); i++) {


            vector[i] =
                    embedding.get(i).floatValue();

        }



        return vector;
    }

}