package com.aiplanner.aiprojectplanner.service;

import com.aiplanner.aiprojectplanner.ai.AIService;
import com.aiplanner.aiprojectplanner.document.DocxExtractor;
import com.aiplanner.aiprojectplanner.document.PDFExtractor;
import com.aiplanner.aiprojectplanner.document.TextExtractor;
import com.aiplanner.aiprojectplanner.dto.ProjectResponseDTO;
import com.aiplanner.aiprojectplanner.entity.Project;
import com.aiplanner.aiprojectplanner.entity.RequirementDocument;
import com.aiplanner.aiprojectplanner.enums.DocumentType;
import com.aiplanner.aiprojectplanner.exception.FileProcessingException;
import com.aiplanner.aiprojectplanner.mapper.ProjectMapper;
import com.aiplanner.aiprojectplanner.repository.DocumentRepository;
import com.aiplanner.aiprojectplanner.repository.ProjectRepository;
import com.aiplanner.aiprojectplanner.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class DocumentServiceImpl implements DocumentService {


    private final AIService aiService;

    private final ProjectRepository projectRepository;

    private final DocumentRepository documentRepository;

    private final ProjectMapper projectMapper;

    private final ChunkService chunkService;



    public DocumentServiceImpl(
            AIService aiService,
            ProjectRepository projectRepository,
            DocumentRepository documentRepository,
            ProjectMapper projectMapper,
            ChunkService chunkService
    ) {

        this.aiService = aiService;
        this.projectRepository = projectRepository;
        this.documentRepository = documentRepository;
        this.projectMapper = projectMapper;
        this.chunkService = chunkService;
    }



    @Override
    public ProjectResponseDTO uploadDocument(MultipartFile file) {


        System.out.println("STEP 1 - Upload received");


        DocumentType documentType =
                FileUtil.getDocumentType(file);



        System.out.println(
                "STEP 2 - Document type: "
                        + documentType
        );



        String extractedText;



        try {


            switch (documentType) {


                case PDF:

                    extractedText =
                            PDFExtractor.extract(file);

                    break;



                case DOCX:

                    extractedText =
                            DocxExtractor.extract(file);

                    break;



                case TXT:

                    extractedText =
                            TextExtractor.extract(file);

                    break;



                default:

                    throw new FileProcessingException(
                            "Only PDF, DOCX and TXT files are supported."
                    );
            }



        } catch (IOException e) {


            throw new FileProcessingException(
                    "Error while extracting document text.",
                    e
            );
        }



        System.out.println(
                "STEP 3 - Text extracted successfully"
        );



        // AI Processing

        ProjectResponseDTO response =
                aiService.analyzeProject(extractedText);



        System.out.println(
                "STEP 4 - AI analysis completed"
        );



        String fileName =
                file.getOriginalFilename();



        if(fileName != null && !fileName.isBlank()) {


            int lastDot =
                    fileName.lastIndexOf(".");


            String projectName =
                    (lastDot > 0)
                            ? fileName.substring(0,lastDot)
                            : fileName;



            response.setProjectName(
                    projectName
            );


        } else {


            response.setProjectName(
                    "Untitled Project"
            );
        }



        System.out.println(
                "STEP 5 - Project name added: "
                        + response.getProjectName()
        );



        // Save Project

        Project project =
                projectMapper.toEntity(response);



        Project savedProject =
                projectRepository.save(project);



        System.out.println(
                "STEP 6 - Project saved with ID: "
                        + savedProject.getId()
        );




        // Save Uploaded Document

        RequirementDocument document =
                new RequirementDocument();



        document.setFileName(
                file.getOriginalFilename()
        );



        document.setFileType(
                documentType.name()
        );



        document.setContent(
                extractedText
        );



        document.setProject(
                savedProject
        );



        RequirementDocument savedDocument =
                documentRepository.save(document);



        System.out.println(
                "STEP 7 - Document saved successfully"
        );



        // RAG Processing
        // Split document into chunks
        // Generate embeddings
        // Store in document_chunks table

        chunkService.processDocument(
                savedDocument.getId(),
                extractedText
        );



        System.out.println(
                "STEP 8 - Document chunks created successfully"
        );



        return response;
    }
}