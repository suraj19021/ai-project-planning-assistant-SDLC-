package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.dto.DocumentUploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    DocumentUploadResponseDTO uploadDocument(MultipartFile file);

}