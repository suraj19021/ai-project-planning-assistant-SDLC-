package com.aiplanner.aiprojectplanner.util;

import com.aiplanner.aiprojectplanner.enums.DocumentType;
import org.springframework.web.multipart.MultipartFile;

public final class FileUtil {

    private FileUtil() {
        // Prevent instantiation
    }

    public static DocumentType getDocumentType(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return DocumentType.UNKNOWN;
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isBlank()) {
            return DocumentType.UNKNOWN;
        }

        fileName = fileName.toLowerCase();

        if (fileName.endsWith(Constants.PDF_EXTENSION)) {
            return DocumentType.PDF;
        }

        if (fileName.endsWith(Constants.DOCX_EXTENSION)) {
            return DocumentType.DOCX;
        }

        if (fileName.endsWith(Constants.TXT_EXTENSION)) {
            return DocumentType.TXT;
        }

        return DocumentType.UNKNOWN;
    }
}