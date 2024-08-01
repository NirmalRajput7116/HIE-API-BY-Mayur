package com.cellbeans.hspa.mstsmstemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSmsTemplateRepository extends JpaRepository<MstSmsTemplate, Long> {

    Page<MstSmsTemplate> findByStNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstSmsTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstSmsTemplate> findByStNameContains(String key);

}
            
