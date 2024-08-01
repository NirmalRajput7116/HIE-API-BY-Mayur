package com.cellbeans.hspa.mradtemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradTemplateRepository extends JpaRepository<MradTemplate, Long> {

    Page<MradTemplate> findByTemplateNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MradTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MradTemplate> findByTemplateNameContains(String key);

}
            
