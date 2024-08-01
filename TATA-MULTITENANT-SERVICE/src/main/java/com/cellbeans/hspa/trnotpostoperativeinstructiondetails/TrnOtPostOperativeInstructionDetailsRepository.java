package com.cellbeans.hspa.trnotpostoperativeinstructiondetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtPostOperativeInstructionDetailsRepository extends JpaRepository<TrnOtPostOperativeInstructionDetails, Long> {

    List<TrnOtPostOperativeInstructionDetails> findAllByOpooidOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtPostOperativeInstructionDetails> findAllByOpooidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtPostOperativeInstructionDetails> findAllByOpooidProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
