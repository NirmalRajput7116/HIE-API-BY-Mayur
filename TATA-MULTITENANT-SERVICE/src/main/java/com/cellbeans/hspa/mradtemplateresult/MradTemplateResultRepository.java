package com.cellbeans.hspa.mradtemplateresult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradTemplateResultRepository extends JpaRepository<MradTemplateResult, Long> {

    Page<MradTemplateResult> findByTrNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MradTemplateResult> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MradTemplateResult> findByTrNameContains(String key);

}
            
