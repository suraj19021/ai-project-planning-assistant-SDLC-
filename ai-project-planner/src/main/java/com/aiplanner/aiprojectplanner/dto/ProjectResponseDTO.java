package com.aiplanner.aiprojectplanner.dto;

import com.aiplanner.aiprojectplanner.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {

    private String projectName;

    private String executiveSummary;

    private String projectObjective;

    private List<ModuleDTO> modules;

    private List<SprintPlanDTO> sprintPlans;

    private String timeline;

    private String resourceRecommendation;

    private String technologyStack;

    private String riskAnalysis;

    private ProjectStatus status;

}