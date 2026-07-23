package com.aiplanner.aiprojectplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "project_module")
public class ProjectModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}