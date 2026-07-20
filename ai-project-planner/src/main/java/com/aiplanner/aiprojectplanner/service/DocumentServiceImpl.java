package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.dto.DocumentUploadResponseDTO;
import com.aiplanner.aiprojectplanner.enums.DocumentType;
import com.aiplanner.aiprojectplanner.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Override
    public DocumentUploadResponseDTO uploadDocument(MultipartFile file) {

        DocumentType documentType = FileUtil.getDocumentType(file);

        return DocumentUploadResponseDTO.builder()
                .fileName(file.getOriginalFilename())
                .documentType(documentType)
                .status("Uploaded Successfully")
                .build();
    }
}