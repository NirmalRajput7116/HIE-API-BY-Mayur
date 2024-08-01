package com.cellbeans.hspa.mradtesttemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradTestTemplateRepository extends JpaRepository<MradTestTemplate, Long> {
    Page<MradTestTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MradTestTemplate> findByTtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    List<MradTestTemplate> findByTtNameContains(String key);
}
