package com.cellbeans.hspa.mstappointmentinstruction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAppointmentInstructionRepository extends JpaRepository<MstAppointmentInstruction, Long> {

    Page<MstAppointmentInstruction> findByAiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAppointmentInstruction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAppointmentInstruction> findByAiNameContains(String key);

    MstAppointmentInstruction findByAiNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
