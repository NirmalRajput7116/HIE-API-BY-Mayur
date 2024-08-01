package com.cellbeans.hspa.trnotintraoperativeinstructiondetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtIntraOperativeInstructionDetailsRepository extends JpaRepository<TrnOtIntraOperativeInstructionDetails, Long> {

    List<TrnOtIntraOperativeInstructionDetails> findAllByOioidOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtIntraOperativeInstructionDetails> findAllByOioidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtIntraOperativeInstructionDetails> findAllByOioidProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
