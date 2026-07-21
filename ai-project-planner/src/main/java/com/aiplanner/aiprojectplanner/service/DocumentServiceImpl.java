package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.document.DocxExtractor;
import com.aiplanner.aiprojectplanner.document.PDFExtractor;
import com.aiplanner.aiprojectplanner.document.TextExtractor;
import com.aiplanner.aiprojectplanner.dto.DocumentUploadResponseDTO;
import com.aiplanner.aiprojectplanner.enums.DocumentType;
import com.aiplanner.aiprojectplanner.exception.FileProcessingException;
import com.aiplanner.aiprojectplanner.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Override
    public DocumentUploadResponseDTO uploadDocument(MultipartFile file) {

        DocumentType documentType = FileUtil.getDocumentType(file);

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

        return DocumentUploadResponseDTO.builder()
                .fileName(file.getOriginalFilename())
                .documentType(documentType)
                .status("Uploaded Successfully")
                .extractedText(extractedText)
                .build();
    }
}