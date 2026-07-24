package com.aiplanner.aiprojectplanner.controller;

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

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> uploadDocument(
            @RequestParam("file") MultipartFile file) {

        ProjectResponseDTO projectResponse =
                documentService.uploadDocument(file);

        byte[] pdf =
                pdfReportGenerator.generateProjectReport(projectResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=AI_Project_Report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}