Project Title
AI Project Planning Assistant (SDLC)
________________________________________
Objective
Develop an AI-powered backend application that analyzes software requirement documents (PDF, DOCX, and TXT) and automatically generates a structured project analysis report. The system extracts document content, leverages AI to understand project requirements, and produces a downloadable project planning report to assist project managers and technical leads during project initiation.
The application also implements a Retrieval-Augmented Generation (RAG) architecture by splitting uploaded documents into semantic chunks, generating vector embeddings using an embedding model, and storing them in PostgreSQL pgvector. This enables semantic similarity search and context-aware AI question answering over uploaded documents, allowing users to interact with project requirements through natural language queries. The system produces a downloadable project planning report to assist project managers and technical leads during project initiation while providing intelligent document retrieval and AI-assisted decision support.
________________________________________
Problem Statement
In software development, project managers and technical leads spend significant time manually reviewing Software Requirement Specifications (SRS), Business Requirement Documents (BRDs), Request for Proposals (RFPs), and other client documents to understand project scope before initiating development activities. In addition, retrieving specific information from lengthy requirement documents and answering project-related questions often requires repeatedly searching through hundreds of pages, making the process slow and inefficient.
This manual approach:
•	Takes considerable time and effort to analyze large requirement documents.
•	Is repetitive and resource-intensive.
•	Delays project planning and decision-making.
•	May lead to inconsistent interpretation of requirements.
•	Makes it difficult to quickly retrieve relevant project information.
•	Slows down communication between stakeholders and technical teams during project initiation.
The AI Project Planning Assistant addresses these challenges by automatically extracting text from uploaded requirement documents, analyzing the content using Large Language Models (LLMs), and generating a structured project planning report, including project summaries, functional modules, sprint planning recommendations, and project timelines.
To further enhance document understanding, the application implements a Retrieval-Augmented Generation (RAG) architecture by splitting documents into semantic chunks, generating vector embeddings, and storing them in PostgreSQL pgvector. This enables semantic similarity search and context-aware AI question answering, allowing users to ask natural language questions about uploaded documents and receive accurate, relevant responses without manually searching through the entire document.
________________________________________
Scope (MVP)
Input
The application accepts software requirement documents in the following formats:
•	PDF
•	DOCX
•	TXT
Documents are uploaded through a REST API and processed by the backend application. After upload, the system automatically extracts the document content, identifies the document type, and stores the extracted text for further AI processing.
AI Processing
Once the document is uploaded, the application:
•	Extracts text from the uploaded requirement document.
•	Analyzes the extracted content using a Large Language Model (LLM).
•	Generates a structured project analysis, including:
o	Executive Summary
o	Functional Modules
o	Suggested Sprint Plan
o	Project Timeline Overview
•	Splits the document into semantic chunks for Retrieval-Augmented Generation (RAG).
•	Generates vector embeddings for each document chunk using an embedding model.
•	Stores the embeddings in PostgreSQL pgvector to enable semantic similarity search.
Output
The application provides:
•	AI-generated project analysis report.
•	Structured project planning information.
•	Semantic document retrieval using vector search.
•	Context-aware AI question answering over uploaded requirement documents through REST APIs.
________________________________________
AI Processing
The system performs the following steps:
•	Validate the uploaded requirement document.
•	Detect the document type (PDF, DOCX, or TXT).
•	Extract text from the uploaded document.
•	Store the extracted document content in the database.
•	Send the extracted content to the AI model using Spring AI and Ollama.
•	Analyze the project requirements using a Large Language Model (LLM).
•	Generate a structured project analysis, including:
o	Executive Summary
o	Functional Modules
o	Suggested Sprint Plan
o	Project Timeline Overview
•	Split the extracted document into semantic chunks.
•	Generate vector embeddings for each chunk using the embedding model.
•	Store document chunks and embeddings in PostgreSQL pgvector.
•	Perform semantic similarity search using Retrieval-Augmented Generation (RAG).
•	Retrieve the most relevant document chunks based on user queries.
•	Generate context-aware answers using the retrieved document context and the LLM.
•	Produce a structured AI-powered project planning report for project managers and technical leads.
________________________________________
Output
The application generates the following outputs:
•	Project Name (derived automatically from the uploaded document name).
•	Executive Summary.
•	Functional Modules identified from the requirement document.
•	Suggested Sprint Plan.
•	Project Timeline Overview.
•	AI-generated project analysis report.
•	Document chunks with generated vector embeddings stored in PostgreSQL pgvector.
•	Semantic similarity search results for relevant document content.
•	Context-aware AI responses to user questions using Retrieval-Augmented Generation (RAG).
•	Downloadable AI Project Planning Report (PDF).

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
        │ PDF  → Apache PDFBox                    │
        │ DOCX → Apache POI                       │
        │ TXT  → Text Reader                      │
        └──────────────────────────────────────────┘
                                │
                                ▼
                     Extracted Requirement Text
                                │
                                ▼
             Store Original Document in Database
                                │
                                ▼
                     AI Processing Layer
              (Spring AI + Ollama - Llama 3.2)
                                │
                                ▼
                  AI Project Analysis
        ┌─────────────────────────────────────────┐
        │ Executive Summary                       │
        │ Functional Modules                      │
        │ Suggested Sprint Plan                   │
        │ Timeline Overview                       │
        └─────────────────────────────────────────┘
                                │
                                ▼
                  ProjectResponseDTO Creation
                                │
                                ▼
             Project Name Extraction
      (Derived automatically from uploaded file name)
                                │
                                ▼
                    RAG Processing Pipeline
        ┌─────────────────────────────────────────┐
        │ Split Document into Chunks              │
        │ Generate Embeddings                     │
        │ Store Embeddings in PostgreSQL          │
        │ (pgvector - vector(768))                │
        └─────────────────────────────────────────┘
                                │
                                ▼
             Semantic Similarity Search (RAG)
                                │
                                ▼
        User Question → Retrieve Relevant Chunks
                                │
                                ▼
         Context + User Query sent to LLM
                                │
                                ▼
            AI Generates Context-Aware Answer
                                │
                                ▼                          
        User Receives AI Analysis + RAG-powered Answers
________________________________________
APIs
1. Upload Requirement Document
Endpoint
POST http://localhost:8080/api/projects/upload
Request
Content-Type: multipart/form-data
Parameter	Type	Description
file	Multipart File	Requirement document (PDF, DOCX, or TXT)
Processing
After receiving the document, the application:
•	Validates the uploaded file.
•	Extracts document text.
•	Stores the original document.
•	Generates AI-based project analysis.
•	Splits the document into semantic chunks.
•	Generates vector embeddings.
•	Stores embeddings in PostgreSQL pgvector.
•	Returns the AI-generated project analysis.
Sample Response
{
  "projectName": "Online Banking System",
  "executiveSummary": "The project aims to develop an online banking platform that enables customers to securely manage accounts, transfer funds, pay bills, and access banking services through web and mobile applications.",
  "functionalModules": [
    "User Authentication",
    "Account Management",
    "Fund Transfer",
    "Bill Payments"
  ],
  "suggestedSprintPlan": [
    "Sprint 1 - Authentication",
    "Sprint 2 - Core Banking",
    "Sprint 3 - Payments"
  ],
  "timelineOverview": "12 Weeks"
}
________________________________________
Tech Stack
Backend
•	Java 21
•	Spring Boot 4.1
•	Spring Web MVC
•	Spring Data JPA
•	Spring AI
•	Hibernate ORM 7.4
•	Hibernate Vector
Artificial Intelligence
•	Spring AI
•	Ollama
•	Llama 3.2 (LLM)
•	Nomic Embed Text (Embedding Model)
•	Retrieval-Augmented Generation (RAG)
Database
•	PostgreSQL
•	pgvector Extension
•	Hibernate (JPA)
Document Processing
•	Apache PDFBox (PDF)
•	Apache POI (DOCX)
•	Java Text Reader (TXT)
Vector Search & Embeddings
•	PostgreSQL pgvector
•	Vector Embeddings (768 Dimensions)
•	Semantic Similarity Search
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
Document Upload & AI Processing Flow
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
            Store Original Document
                         │
                         ▼
      AI Processing (Spring AI + Ollama)
                         │
                         ▼
          Generate Project Analysis
                         │
                         ▼
        Create ProjectResponseDTO
                         │
                         ▼
          Store Project Information
                         │
                         ▼
      Split Document into Chunks (RAG)
                         │
                         ▼
     Generate Embeddings (Nomic Embed Text)
                         │
                         ▼
 Store Embeddings in PostgreSQL (pgvector)
                         │
                         ▼
        Return AI Project Analysis
                         │
                         ▼
       Download Project Planning Report
AI Question Answering Flow (RAG)
                    Open Postman
                         │
                         ▼
           Select POST /api/ai/ask
                         │
                         ▼
          Enter Natural Language Query
                         │
                         ▼
                  Send Request
                         │
                         ▼
         Generate Query Embedding
                         │
                         ▼
 Semantic Search in PostgreSQL (pgvector)
                         │
                         ▼
      Retrieve Relevant Document Chunks
                         │
                         ▼
  Combine Retrieved Context + User Query
                         │
                         ▼
      AI Processing (Spring AI + Ollama)
                         │
                         ▼
      Generate Context-Aware Response
                         │
                         ▼
          Return AI Answer to User
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
|    ├── ChunkService
|    ├── ChunkServiceImpl
|    ├── EmbeddingService
|    ├── EmbeddingServiceImpl
|    ├── RetrievalService.java
|    ├── RetrievalServiceImpl.java
│   ├── ProjectService.java
|    ├── ProjectServiceImpl.java
│   └── DocumentService.java
│   └── DocumentServiceImpl.java

│
├── repository
│   ├── ChunkRepository.java
│   ├── ProjectRepository.java
│   └── DocumentRepository.java
│
├── entity
│   ├── Project.java
│   ├──DocumentChunk.java
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
├── rag
│   ├──Chunk.java
│   ├──Embedding.java
│   ├── Prompt.java
│   ├──Retrieval.java
│   └──Vectorstore.java
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
The AI Project Planning Assistant can be further enhanced with the following capabilities:
•	Proposal Generation – Automatically generate project proposals from client requirement documents.
•	User Story Generation – Create Agile user stories with acceptance criteria directly from software requirements.
•	Test Case Generation – Generate functional and integration test cases based on analyzed project requirements.
•	Jira Integration – Automatically create Epics, Stories, Tasks, and Sprint Backlogs in Jira.
•	Historical Project Matching – Recommend similar completed projects using vector similarity search and historical project data.
•	Advanced RAG Knowledge Base – Support multiple uploaded documents and organization-wide knowledge repositories for more accurate AI responses.
•	Resource Planning – Recommend team composition, skill requirements, and resource allocation based on project scope.
•	Timeline Estimation – Predict project timelines using AI-driven effort estimation and historical project data.
•	Cost Estimation – Estimate project cost based on project size, complexity, team structure, and duration.
•	Architecture Recommendation – Suggest suitable software architecture patterns (Monolithic, Microservices, Event-Driven, etc.) and recommended technology stack based on project requirements.
•	Multi-LLM Support – Integrate additional Large Language Models such as OpenAI GPT, Claude, Gemini, and Mistral alongside Ollama.
•	Interactive AI Chat Assistant – Enable conversational interaction with uploaded requirement documents using Retrieval-Augmented Generation (RAG).
•	Cloud Deployment – Deploy the application on cloud platforms such as AWS, Azure, or Google Cloud with scalable infrastructure.

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
cd "E:\SDLC project"
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


