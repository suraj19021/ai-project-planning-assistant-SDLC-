package com.aiplanner.aiprojectplanner.mapper;

import com.aiplanner.aiprojectplanner.dto.DocumentUploadResponseDTO;
import com.aiplanner.aiprojectplanner.entity.RequirementDocument;
import com.aiplanner.aiprojectplanner.enums.DocumentType;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public RequirementDocument toEntity(DocumentUploadResponseDTO dto) {

        RequirementDocument document = new RequirementDocument();

        document.setFileName(dto.getFileName());
        document.setFileType(dto.getDocumentType().name());
        document.setContent(dto.getExtractedText());

        return document;
    }

    public DocumentUploadResponseDTO toDTO(RequirementDocument entity) {

        DocumentUploadResponseDTO dto = new DocumentUploadResponseDTO();

        dto.setFileName(entity.getFileName());
        dto.setDocumentType(DocumentType.valueOf(entity.getFileType()));
        dto.setExtractedText(entity.getContent());

        return dto;
    }
}