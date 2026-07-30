package com.aiplanner.aiprojectplanner.ai;

import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;

public interface AIService {

    ProjectResponseDTO analyzeProject(String documentText);

    String askQuestion(String question);

}