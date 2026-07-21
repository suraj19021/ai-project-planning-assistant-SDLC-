package com.aiplanner.aiprojectplanner.controller;

import com.aiplanner.aiprojectplanner.dto.DocumentUploadResponseDTO;
import com.aiplanner.aiprojectplanner.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final DocumentService documentService;

    public ProjectController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentUploadResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(documentService.uploadDocument(file));
    }
}