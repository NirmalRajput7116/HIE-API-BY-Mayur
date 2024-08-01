package com.cellbeans.hspa.memrcomplexcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrComplexCategoryRepository extends JpaRepository<MemrComplexCategory, Long> {

    Page<MemrComplexCategory> findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrComplexCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrComplexCategory> findByCcNameContains(String key);

}
            
