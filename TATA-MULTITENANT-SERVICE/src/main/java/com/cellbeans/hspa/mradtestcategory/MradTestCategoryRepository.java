package com.cellbeans.hspa.mradtestcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradTestCategoryRepository extends JpaRepository<MradTestCategory, Long> {

    Page<MradTestCategory> findByTcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MradTestCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MradTestCategory> findByTcNameContains(String key);

}
            
