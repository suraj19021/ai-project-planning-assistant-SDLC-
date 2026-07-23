package com.aiplanner.aiprojectplanner.controller;

import com.aiplanner.aiprojectplanner.dto.DocumentUploadResponseDTO;
import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.report.PDFReportGenerator;
import com.aiplanner.aiprojectplanner.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final DocumentService documentService;
    private final PDFReportGenerator pdfReportGenerator;

    public ProjectController(DocumentService documentService,
                             PDFReportGenerator pdfReportGenerator) {
        this.documentService = documentService;
        this.pdfReportGenerator = pdfReportGenerator;
    }

    /**
     * Upload Requirement Document
     */
    @PostMapping("/upload")
    public ResponseEntity<DocumentUploadResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file) {

        DocumentUploadResponseDTO response = documentService.uploadDocument(file);

        return ResponseEntity.ok(response);
    }

    /**
     * Generate AI Project Report PDF
     */
    @PostMapping(value = "/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(
            @RequestBody ProjectResponseDTO projectResponseDTO) {

        byte[] pdf = pdfReportGenerator.generateProjectReport(projectResponseDTO);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=AI_Project_Report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}