package com.cellbeans.hspa.trnotpreoperativeinstructiondetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtPreOperativeInstructionDetailsRepository extends JpaRepository<TrnOtPreOperativeInstructionDetails, Long> {

    List<TrnOtPreOperativeInstructionDetails> findAllByOpoidOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtPreOperativeInstructionDetails> findAllByOpoidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtPreOperativeInstructionDetails> findAllByOpoidProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
