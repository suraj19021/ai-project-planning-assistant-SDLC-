package com.aiplanner.aiprojectplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIProjectAnalysisDTO {

    private String executiveSummary;

    private String projectObjective;

    private List<String> functionalModules;

    private String technologyStack;

    private String sprintPlan;

    private String timeline;

    private String resourceRecommendation;
}