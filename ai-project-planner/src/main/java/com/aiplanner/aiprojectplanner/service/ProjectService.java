package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.dto.ProjectRequestDTO;
import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;

public interface ProjectService {

    ProjectResponseDTO analyzeProject(ProjectRequestDTO request);

}