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
public class SprintPlanDTO {

    private String sprintName;

    private String sprintGoal;

    private List<String> features;

    private Integer durationInWeeks;

}
