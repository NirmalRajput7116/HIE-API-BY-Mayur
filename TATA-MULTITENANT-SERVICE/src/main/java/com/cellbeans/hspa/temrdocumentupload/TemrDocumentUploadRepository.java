package com.cellbeans.hspa.temrdocumentupload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrDocumentUploadRepository extends JpaRepository<TemrDocumentUpload, Long> {

    Page<TemrDocumentUpload> findByDuVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitId, Pageable page);

    Page<TemrDocumentUpload> findByDuPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long patientId, Pageable page);

    Page<TemrDocumentUpload> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrDocumentUpload> findByDuDcIdContains(String key);

}
            
