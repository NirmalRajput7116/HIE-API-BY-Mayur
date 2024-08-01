package com.cellbeans.hspa.motproceduretype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotProcedureTypeRepository extends JpaRepository<MotProcedureType, Long> {

    Page<MotProcedureType> findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotProcedureType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotProcedureType> findByPtNameContains(String key);

}
            
