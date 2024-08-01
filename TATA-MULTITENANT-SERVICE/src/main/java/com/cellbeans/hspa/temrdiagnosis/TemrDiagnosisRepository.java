package com.cellbeans.hspa.temrdiagnosis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrDiagnosisRepository extends JpaRepository<TemrDiagnosis, Long> {

    Page<TemrDiagnosis> findByDiagnosisDescriptionContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrDiagnosis> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//        List<TemrDiagnosis> findByDCodeContains(String key);
}
            
