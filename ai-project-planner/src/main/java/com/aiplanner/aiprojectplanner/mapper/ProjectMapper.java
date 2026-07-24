package com.aiplanner.aiprojectplanner.mapper;

import com.aiplanner.aiprojectplanner.dto.ModuleDTO;
import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.dto.SprintPlanDTO;
import com.aiplanner.aiprojectplanner.entity.*;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper {


    public Project toEntity(ProjectResponseDTO dto) {

        Project project = new Project();

        project.setProjectName(dto.getProjectObjective());
        project.setExecutiveSummary(dto.getExecutiveSummary());
        project.setTimeline(dto.getTimeline());


        if(dto.getModules() != null) {

            project.setModules(
                    dto.getModules()
                            .stream()
                            .map(moduleDTO -> {

                                ProjectModule module = new ProjectModule();

                                module.setModuleName(moduleDTO.getModuleName());
                                module.setDescription(moduleDTO.getDescription());
                                module.setStatus("PLANNED");
                                module.setProject(project);

                                return module;

                            })
                            .collect(Collectors.toList())
            );
        }


        if(dto.getSprintPlans() != null) {

            project.setSprintPlans(
                    dto.getSprintPlans()
                            .stream()
                            .map(sprintDTO -> {

                                SprintPlan sprint = new SprintPlan();

                                sprint.setSprintName(sprintDTO.getSprintName());

                                sprint.setDuration(
                                        sprintDTO.getDurationInWeeks() != null
                                                ? sprintDTO.getDurationInWeeks() + " Weeks"
                                                : null
                                );

                                sprint.setTasks(
                                        sprintDTO.getFeatures() != null
                                                ? String.join(", ", sprintDTO.getFeatures())
                                                : null
                                );

                                sprint.setProject(project);

                                return sprint;

                            })
                            .collect(Collectors.toList())
            );
        }


        return project;
    }
}