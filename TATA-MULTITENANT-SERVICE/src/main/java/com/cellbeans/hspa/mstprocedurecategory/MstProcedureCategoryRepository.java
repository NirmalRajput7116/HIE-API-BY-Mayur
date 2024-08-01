package com.cellbeans.hspa.mstprocedurecategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstProcedureCategoryRepository extends JpaRepository<MstProcedureCategory, Long> {

    Page<MstProcedureCategory> findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstProcedureCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstProcedureCategory> findByPcNameContains(String key);

}
            
