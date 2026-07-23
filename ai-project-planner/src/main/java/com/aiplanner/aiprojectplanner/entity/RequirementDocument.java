package com.aiplanner.aiprojectplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "requirement_document")
public class RequirementDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}