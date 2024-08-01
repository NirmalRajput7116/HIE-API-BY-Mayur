package com.cellbeans.hspa.motprocedurecategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotProcedureCategoryRepository extends JpaRepository<MotProcedureCategory, Long> {

    Page<MotProcedureCategory> findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotProcedureCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotProcedureCategory> findByPcNameContains(String key);

}
            
