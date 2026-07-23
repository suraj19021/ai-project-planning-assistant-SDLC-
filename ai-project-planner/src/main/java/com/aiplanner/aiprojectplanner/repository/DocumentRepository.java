package com.aiplanner.aiprojectplanner.repository;

import com.aiplanner.aiprojectplanner.entity.RequirementDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<RequirementDocument, Long> {

}