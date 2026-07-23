package com.aiplanner.aiprojectplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sprint_plan")
public class SprintPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sprintName;

    private String duration;

    @Column(columnDefinition = "TEXT")
    private String tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}