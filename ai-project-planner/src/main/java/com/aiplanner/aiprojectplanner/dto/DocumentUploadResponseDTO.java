package com.aiplanner.aiprojectplanner.dto;

import com.aiplanner.aiprojectplanner.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadResponseDTO {

    private String fileName;

    private DocumentType documentType;

    private String status;

}