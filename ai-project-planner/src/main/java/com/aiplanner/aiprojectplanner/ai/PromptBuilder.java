package com.aiplanner.aiprojectplanner.ai;

public class PromptBuilder {

    private PromptBuilder() {
    }

    public static String buildExecutiveSummaryPrompt(String documentText) {

        return """
You are a Senior Software Architect, Project Manager, Scrum Master, Business Analyst, and Solution Architect with 15+ years of experience.

Analyze the following Software Requirement Specification (SRS), Business Requirement Document (BRD), or Project Requirement Document.

Generate a complete software project planning report.

==================================================

1. Executive Summary
Write a concise executive summary (150–200 words).

==================================================

2. Project Objective

Explain:
• Business goal
• Problem being solved
• Expected business value
• Target users

==================================================

3. Functional Modules

Identify every major module.

For each module provide:

Module Name

Purpose

Priority (High / Medium / Low)

==================================================

4. Suggested Technology Stack

Recommend:

Backend

Frontend

Database

Cloud Platform

Authentication

API Documentation

CI/CD

Containerization

Explain why each technology is suitable.

==================================================

5. Sprint Planning

Divide the project into 4 Agile Sprints.

For every sprint provide:

Sprint Goal

Features

Deliverables

Estimated Duration

==================================================

6. Timeline Estimation

Provide

Planning

Design

Development

Testing

Deployment

Overall Timeline

==================================================

7. Resource Recommendation

Recommend the ideal team.

Mention

Project Manager

Business Analyst

Backend Developers

Frontend Developers

QA Engineers

DevOps Engineer

UI/UX Designer

Database Administrator

Also explain why each role is required.

==================================================

8. Risk Analysis

Mention

Technical Risks

Business Risks

Deployment Risks

Mitigation Strategy

==================================================

9. Development Approach

Explain

Architecture

Microservices vs Monolith

API Strategy

Database Strategy

Deployment Strategy

==================================================

Requirement Document

%s
""".formatted(documentText);

    }

}