package com.cellbeans.hspa.mpathtesttemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathTestTemplateRepository extends JpaRepository<MpathTestTemplate, Long> {

    Page<MpathTestTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MpathTestTemplate> findByTtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<MpathTestTemplate> findByTtNameContains(String key);

}
