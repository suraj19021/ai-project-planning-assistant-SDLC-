package com.aiplanner.aiprojectplanner.document;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TextExtractor {

    private TextExtractor() {
    }

    public static String extract(MultipartFile file) throws IOException {

        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}