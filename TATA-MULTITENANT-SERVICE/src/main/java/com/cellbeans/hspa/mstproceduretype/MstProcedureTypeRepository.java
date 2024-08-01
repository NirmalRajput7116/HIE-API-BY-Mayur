package com.cellbeans.hspa.mstproceduretype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstProcedureTypeRepository extends JpaRepository<MstProcedureType, Long> {

    Page<MstProcedureType> findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstProcedureType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstProcedureType> findByPtNameContains(String key);

}
            
