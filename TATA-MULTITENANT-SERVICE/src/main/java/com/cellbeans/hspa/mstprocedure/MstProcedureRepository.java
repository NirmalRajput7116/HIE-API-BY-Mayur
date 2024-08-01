package com.cellbeans.hspa.mstprocedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstProcedureRepository extends JpaRepository<MstProcedure, Long> {

    Page<MstProcedure> findByProcedureNameContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureDurationContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureNoteContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureAnaesthesiaTypeAtNameContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureOperationTableOttNameContainsAndIsActiveTrueAndIsDeletedFalseOrAndProcedureOperationTheatreOtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name5, String name4, String name3, String name2, String name1, Pageable page);

    Page<MstProcedure> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstProcedure> findByProcedureNameContains(String key);

}
            
