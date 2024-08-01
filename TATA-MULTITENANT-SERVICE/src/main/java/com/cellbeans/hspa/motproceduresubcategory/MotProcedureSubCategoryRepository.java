package com.cellbeans.hspa.motproceduresubcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotProcedureSubCategoryRepository extends JpaRepository<MotProcedureSubCategory, Long> {

    Page<MotProcedureSubCategory> findByPscNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotProcedureSubCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotProcedureSubCategory> findByPscNameContains(String key);

}
            
