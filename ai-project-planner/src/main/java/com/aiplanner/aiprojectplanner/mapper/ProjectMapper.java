package com.aiplanner.aiprojectplanner.mapper;

import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectResponseDTO dto) {

        Project project = new Project();

        project.setProjectName(dto.getProjectName());
        project.setExecutiveSummary(dto.getExecutiveSummary());
        project.setTimeline(dto.getTimeline());

        return project;
    }

    public ProjectResponseDTO toDTO(Project entity) {

        ProjectResponseDTO dto = new ProjectResponseDTO();

        dto.setProjectName(entity.getProjectName());
        dto.setExecutiveSummary(entity.getExecutiveSummary());
        dto.setTimeline(entity.getTimeline());

        return dto;
    }
}