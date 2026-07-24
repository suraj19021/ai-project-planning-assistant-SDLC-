package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    ProjectResponseDTO uploadDocument(MultipartFile file);
}