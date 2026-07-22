package com.aiplanner.aiprojectplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDTO {

    private String moduleName;

    private String description;

    private String priority;

}
