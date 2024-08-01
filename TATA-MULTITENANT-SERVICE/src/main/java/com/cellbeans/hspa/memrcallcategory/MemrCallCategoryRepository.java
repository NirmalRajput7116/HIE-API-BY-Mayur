package com.cellbeans.hspa.memrcallcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrCallCategoryRepository extends JpaRepository<MemrCallCategory, Long> {
    MemrCallCategory findByCallCategoryId(Long id);

    Page<MemrCallCategory> findByCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCallCategoryName(String name, Pageable page);
//    Page<MemrCallCategory> findByccsNameContainsAndIsActiveTrueAndIsDeletedFalseOrCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MemrCallCategory> findAllByIsActiveTrueAndIsDeletedFalseOrderByCallCategoryName(Pageable page);

    List<MemrCallCategory> findByCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrCallCategory findByCallCategoryNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

    Page<MemrCallCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
}
