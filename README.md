Project Title
AI Project Planning Assistant (SDLC)
________________________________________
Objective
Develop an AI-powered backend application that analyzes software requirement documents (PDF, DOCX, and TXT) and automatically generates a structured project analysis report. The system extracts document content, leverages AI to understand project requirements, and produces a downloadable project planning report to assist project managers and technical leads during project initiation.
________________________________________
Problem Statement
In software development, project managers and technical leads spend significant time manually reviewing Software Requirement Specifications (SRS), Business Requirement Documents (BRDs), Request for Proposals (RFPs), and other client documents to understand project scope before planning development activities.
This manual approach:
•	Takes considerable time 
•	Is repetitive and resource-intensive 
•	Delays project planning 
•	May lead to inconsistent interpretation of requirements 
•	Slows down project initiation 
The AI Project Planning Assistant automates this process by extracting text from requirement documents, analyzing it using AI, and generating a structured project planning report within minutes.
________________________________________
Scope (MVP)
Input
The application accepts requirement documents in the following formats:
•	PDF 
•	DOCX 
•	TXT 
The document is uploaded through a REST API using Postman.
________________________________________
AI Processing
The system performs the following steps:
•	Validate the uploaded document 
•	Extract text from the document 
•	Send the extracted content to the AI model (Spring AI + Ollama) 
•	Analyze the project requirements 
•	Generate an executive summary 
•	Identify the project objective 
•	Suggest timeline recommendations 
•	Recommend technology stack 
•	Generate resource recommendations 
•	Perform basic risk analysis 
•	Create a structured project report 
________________________________________
Output
The application generates:
•	Project Name (derived from the uploaded document) 
•	Executive Summary 
•	Project Objective 
•	Timeline Recommendation 
•	Technology Stack Recommendation 
•	Resource Recommendation 
•	Risk Analysis 
•	Downloadable AI Project Report (PDF)

________________________________________
User Flow
                        AI PROJECT PLANNING ASSISTANT
--------------------------------------------------------------------------------

                    User Uploads Requirement Document
                 (PDF / DOCX / TXT through REST API)
                                │
                                ▼
                     Document Validation
             (File Type & Size Validation)
                                │
                                ▼
                     Text Extraction Layer
        ┌──────────────────────────────────────────┐
        │ PDF  → PDFBox                            │
        │ DOCX → Apache POI                        │
        │ TXT  → Text Reader                       │
        └──────────────────────────────────────────┘
                                │
                                ▼
                     Extracted Requirement Text
                                │
                                ▼
                      PromptBuilder
       (Creates structured prompt for the AI Model)
                                │
                                ▼
                       AI Processing Layer
              (Spring AI + Ollama Llama 3.2)
                                │
                                ▼
                  AI Project Analysis
        ┌─────────────────────────────────────────┐
        │ Executive Summary                       │
        │ Project Objective                       │
        │ Functional Modules                      │
        │ Suggested Sprint Plan                   │
        │ Timeline Recommendation                 │
        │ Technology Stack                        │
        │ Resource Recommendation                 │
        │ Risk Analysis                           │
        └─────────────────────────────────────────┘
                                │
                                ▼
                 ProjectResponseDTO Creation
                                │
                                ▼
               Project Name Extraction
      (Derived from uploaded file name if AI doesn't provide one)
                                │
                                ▼
                PDF Report Generation
            (iText/OpenPDF Report Generator)
                                │
                                ▼
             AI_Project_Report.pdf Generated
                                │
                                ▼
           HTTP Response (application/pdf)
                                │
                                ▼
                User Downloads Final Report
________________________________________
APIs
1. Upload Requirement
http://localhost:8080/api/projects/upload
Request
Multipart File
Response
{
   "documentId":1,
   "status":"Uploaded Successfully"
}
________________________________________
Tech Stack
Backend
•	Java 21 
•	Spring Boot 4.1 
•	Spring Web MVC 
•	Spring Data JPA 
AI
•	Spring AI 
•	Ollama (Llama 3.2) 
Database
•	PostgreSQL 
•	Hibernate (JPA) 
Document Processing
•	Apache PDFBox (PDF) 
•	Apache POI (DOCX) 
PDF Report Generation
•	OpenPDF (iText-compatible) 
API Testing
•	Postman 
Build Tool
•	Maven 
Utilities
•	Lombok 
Version Control
•	Git 
•	GitHub
________________________________________

Postman Demo Flow
Postman API Testing Flow

Open Postman
      │
      ▼
Select POST /api/projects/upload
      │
      ▼
Upload Requirement Document (PDF/DOCX/TXT)
      │
      ▼
Send Request
      │
      ▼
Document Validation
      │
      ▼
Text Extraction
      │
      ▼
AI Processing (Spring AI + Ollama)
      │
      ▼
Generate Project Analysis
      │
      ▼
Generate PDF Report
      ________________________________________
Folder Structure
src/main/java
com.aiplanner
│
├── AiProjectPlanningAssistantSdlcApplication.java
│
├── controller
│   └── ProjectController.java
│   └── AIController.java

│
├── service
│   ├── ProjectService.java
|    ├── ProjectServiceImpl.java
│   └── DocumentService.java
│   └── DocumentServiceImpl.java

│
├── repository
│   ├── ProjectRepository.java
│   └── DocumentRepository.java
│
├── entity
│   ├── Project.java
│   ├── RequirementDocument.java
│   ├── SprintPlan.java
│   └── ProjectModule.java
│
├── dto
│   ├── ProjectRequestDTO.java
│   ├── ProjectResponseDTO.java
│   ├── SprintPlanDTO.java
│   ├── ModuleDTO.java
│   └── DocumentUploadResponseDTO.java
|
├── Report
│   ├── PDFReportGenerator.java
│
├── config
│   ├──OllamaConfig.java
│   └── ApplicationConfig.java
│
├── ai
│   ├── AIService.java
│   ├── PromptBuilder.java
│   └── AIServiceImpl.java
│
├── document
│   ├── PDFExtractor.java
│   ├── DocxExtractor.java
│   └── TextExtractor.java
│
├── mapper
│   ├── DocumentMapper.java
│   └── ProjectMappper.java
│
├── util
│   ├── FileUtil.java
│   └── Constants.java
│
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── FileProcessingException.java
│   └── AIProcessingException.java
│
└── enums
    ├── DocumentType.java
    └── ProjectStatus.java
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

Command	Description
git branch	Check current branch
git checkout feature/suraj	Move to feature branch
git checkout main	Move to main branch
git status	Check modified/staged files
git pull origin feature/suraj	Get latest code from feature branch
git pull origin main	Get latest code from main branch
git add .	Add all changed files for commit
git commit -m "message"	Save changes with commit message
git push origin feature/suraj	Push changes to feature branch
git push origin main	Push changes to main branch
git log --oneline	View commit history
git branch -a	View all local + remote branches
git branch -r	View GitHub remote branches
git remote -v	Check connected GitHub repository
git merge feature/suraj	Merge feature branch into current branch
git checkout -b branch-name	Create and switch to new branch
git clone repo-url	Download repository first time
git fetch	Get remote updates without merging
git diff	Check code changes before commit
git restore filename	Undo local file changes
git stash	Temporarily save unfinished changes
git stash pop	Restore stashed changes

Your project daily flow:
Start work:
git checkout feature/your_name
git pull origin feature/your_name
After coding:
cd "E:\SDLC project "
git status
git add .
git commit -m "Add database entity structure"
git push origin feature/your_name
cd "E:\SDLC project\ai-project-planner"
After verification:
git checkout main
git pull origin main
git merge feature/your_name
git push origin main


