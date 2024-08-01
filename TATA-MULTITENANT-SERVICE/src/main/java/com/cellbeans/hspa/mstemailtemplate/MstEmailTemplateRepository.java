package com.cellbeans.hspa.mstemailtemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstEmailTemplateRepository extends JpaRepository<MstEmailTemplate, Long> {

    Page<MstEmailTemplate> findByEtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstEmailTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstEmailTemplate> findByEtNameContains(String key);

}
            
