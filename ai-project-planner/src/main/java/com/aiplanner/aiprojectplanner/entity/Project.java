package com.aiplanner.aiprojectplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "executive_summary", columnDefinition = "TEXT")
    private String executiveSummary;

    private String timeline;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<RequirementDocument> documents;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectModule> modules;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<SprintPlan> sprintPlans;
}