package com.cellbeans.hspa.temrlocalexamination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrLocalExaminationRepository extends JpaRepository<TemrLocalExamination, Long> {

    Page<TemrLocalExamination> findByLeIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrLocalExamination> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//        List<TemrLocalExamination> findByDepartmentNameContains(String key);
}
            
