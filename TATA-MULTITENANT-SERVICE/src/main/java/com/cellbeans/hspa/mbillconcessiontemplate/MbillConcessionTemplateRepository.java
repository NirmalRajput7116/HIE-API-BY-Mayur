package com.cellbeans.hspa.mbillconcessiontemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillConcessionTemplateRepository extends JpaRepository<MbillConcessionTemplate, Long> {

    Page<MbillConcessionTemplate> findByCtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillConcessionTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillConcessionTemplate> findByCtNameContains(String key);

}
            
