package com.cellbeans.hspa.memrresolutioncategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrResolutionCategoryRepository extends JpaRepository<MemrResolutionCategory, Long> {
    MemrResolutionCategory findByResolutionCategoryId(Long id);

    Page<MemrResolutionCategory> findByResolutionCategoryNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByResolutionCategoryName(String name, Pageable page);

    Page<MemrResolutionCategory> findAllByIsActiveTrueAndIsDeletedFalseOrderByResolutionCategoryName(Pageable page);

    List<MemrResolutionCategory> findByResolutionCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrResolutionCategory findByResolutionCategoryNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
