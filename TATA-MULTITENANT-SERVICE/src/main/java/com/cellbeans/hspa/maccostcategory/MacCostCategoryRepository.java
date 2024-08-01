package com.cellbeans.hspa.maccostcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacCostCategoryRepository extends JpaRepository<MacCostCategory, Long> {

    Page<MacCostCategory> findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacCostCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacCostCategory> findByCcNameContains(String key);

}
            
