# ai-project-planning-assistant-SDLC-
________________________________________
Objective
Develop an AI-powered backend application that analyzes software requirement documents and automatically generates a project summary and an initial sprint plan to assist project managers and technical leads during project initiation.
________________________________________
Problem Statement
When a new software project is received, project managers and technical leads manually read lengthy requirement documents (SRS, BRD, client requirements, RFPs) to understand the project scope and prepare an initial sprint plan.
This manual process:
•	Takes considerable time 
•	Is repetitive 
•	Delays project planning 
•	Depends on individual interpretation 
The proposed AI Project Planning Assistant automates this process by analyzing the requirement document and generating structured planning information within minutes.
________________________________________
Scope (MVP)
Input
•	Upload Requirement Document 
o	PDF 
o	DOCX 
o	TXT 
OR
•	Paste Requirement Text 
________________________________________
AI Processing
The AI will:
•	Read the requirement 
•	Understand the project 
•	Identify modules 
•	Generate a concise summary 
•	Suggest sprint planning 
________________________________________
Output
•	Executive Summary 
•	Functional Modules 
•	Suggested Sprint Plan 
•	Timeline Overview 
•	Downloadable Report (optional) 
________________________________________
User Flow
Upload Requirement
        │
        ▼
Text Extraction
        │
        ▼
AI Processing
        │
        ▼
Generate Summary
        │
        ▼
Generate Sprint Plan
        │
        ▼
Return JSON Response
________________________________________
APIs
1. Upload Requirement
POST /api/project/upload
Request
Multipart File
Response
{
   "documentId":1,
   "status":"Uploaded Successfully"
}
________________________________________
2. Analyze Requirement
POST /api/project/analyze/{documentId}
Response
{
   "projectName":"Employee Management System",
   "summary":"...",
   "modules":[
      "Authentication",
      "Employee Management",
      "Leave Management",
      "Reports"
   ]
}
________________________________________
3. Generate Sprint Plan
POST /api/project/sprint-plan/{documentId}
Response
{
   "Sprint 1":[
      "Authentication",
      "User Roles"
   ],
   "Sprint 2":[
      "Employee Module"
   ],
   "Sprint 3":[
      "Leave Module"
   ],
   "Sprint 4":[
      "Reports",
      "Testing"
   ]
}
________________________________________
4. Get Complete Analysis
GET /api/project/report/{documentId}
Returns
{
   "summary":{},
   "modules":[],
   "sprints":[]
}
________________________________________
Tech Stack
Backend
•	Java 21 
•	Spring Boot 
AI
•	Spring AI (or OpenAI API) 
Database
•	PostgreSQL 
Security
•	Spring Security + JWT (optional for MVP) 
Document Processing
•	Apache PDFBox 
•	Apache POI 
API Testing
•	Swagger/OpenAPI 
________________________________________
Swagger Demo Flow
Open Swagger
↓
Upload PDF
↓
Click Analyze
↓s
AI returns Summary
↓
Click Sprint Plan
↓
AI returns Sprint Planning
↓
Click Report
↓
Complete JSON Output
________________________________________
Folder Structure
src/main/java
-controller
-service
-repository
-entity
-dto
-config
-ai
-util
-exception
________________________________________
Future Scope
•	Proposal Generation 
•	User Story Generation 
•	Test Case Generation 
•	Jira Integration 
•	Historical Project Matching 
•	Resource Planning 
•	Timeline Estimation 
•	Cost Estimation 
•	Architecture Recommendation

