package com.cellbeans.hspa.trnotproceduredetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtProcedureDetailsRepository extends JpaRepository<TrnOtProcedureDetails, Long> {
    Page<TrnOtProcedureDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnOtProcedureDetails> findAll();
//    List<TrnOtProcedureDetails> findAllByOpdProcedureDateEqualsAndOpdOtIdOtIdEqualsAndOpdTableIdOttIdEqualsAndIsActiveTrueAndIsDeletedFalse(String key, Long keya, Long tableid);

    TrnOtProcedureDetails findAllByOpdPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long patientId);

    Page<TrnOtProcedureDetails> findAllByOpdIsPerformedFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

}
