package com.cellbeans.hspa.trnotprocedureschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtProcedureScheduleRepository extends JpaRepository<TrnOtProcedureSchedule, Long> {
    Page<TrnOtProcedureSchedule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TrnOtProcedureSchedule> findAllByOpsIsScheduleFalseAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnOtProcedureSchedule> findAll();
//    List<TrnOtProcedureSchedule> findAllByOpsProcedureDateEqualsAndOpsOtIdOtIdEqualsAndOpsTableIdOttIdEqualsAndIsActiveTrueAndIsDeletedFalse(String key, Long keya, Long tableid);

    TrnOtProcedureSchedule findAllByOpsPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long patientId);

}
            
