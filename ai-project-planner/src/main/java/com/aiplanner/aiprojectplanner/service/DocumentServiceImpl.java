package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.ai.AIService;
import com.aiplanner.aiprojectplanner.document.DocxExtractor;
import com.aiplanner.aiprojectplanner.document.PDFExtractor;
import com.aiplanner.aiprojectplanner.document.TextExtractor;
import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.enums.DocumentType;
import com.aiplanner.aiprojectplanner.exception.FileProcessingException;
import com.aiplanner.aiprojectplanner.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final AIService aiService;

    public DocumentServiceImpl(AIService aiService) {
        this.aiService = aiService;
    }

    @Override
    public ProjectResponseDTO uploadDocument(MultipartFile file) {

        System.out.println("STEP 1 - Upload received");

        DocumentType documentType = FileUtil.getDocumentType(file);

        System.out.println("STEP 2 - Document type: " + documentType);

        String extractedText;

        try {

            switch (documentType) {

                case PDF:
                    extractedText = PDFExtractor.extract(file);
                    break;

                case DOCX:
                    extractedText = DocxExtractor.extract(file);
                    break;

                case TXT:
                    extractedText = TextExtractor.extract(file);
                    break;

                default:
                    throw new FileProcessingException("Only PDF, DOCX and TXT files are supported.");
            }

        } catch (IOException e) {
            throw new FileProcessingException("Error while extracting document text.", e);
        }

        System.out.println("STEP 3 - Text extracted successfully");

        ProjectResponseDTO response = aiService.analyzeProject(extractedText);

        System.out.println("STEP 4 - AI analysis completed");

        // Extract project name from uploaded file name
        String fileName = file.getOriginalFilename();

        if (fileName != null && !fileName.isBlank()) {

            int lastDot = fileName.lastIndexOf('.');

            String projectName = (lastDot > 0)
                    ? fileName.substring(0, lastDot)
                    : fileName;

            response.setProjectName(projectName);
        } else {
            response.setProjectName("Untitled Project");
        }

        return response;
    }
}