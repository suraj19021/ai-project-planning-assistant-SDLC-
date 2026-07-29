package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.entity.DocumentChunk;
import com.aiplanner.aiprojectplanner.repository.ChunkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChunkServiceImpl implements ChunkService {


    private final ChunkRepository chunkRepository;


    public ChunkServiceImpl(ChunkRepository chunkRepository) {
        this.chunkRepository = chunkRepository;
    }


    @Override
    public List<DocumentChunk> saveChunks(
            List<DocumentChunk> chunks) {

        return chunkRepository.saveAll(chunks);
    }


    @Override
    public List<DocumentChunk> getAllChunks() {

        return chunkRepository.findAll();
    }

}