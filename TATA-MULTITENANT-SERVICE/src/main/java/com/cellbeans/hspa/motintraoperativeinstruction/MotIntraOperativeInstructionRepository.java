package com.cellbeans.hspa.motintraoperativeinstruction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotIntraOperativeInstructionRepository extends JpaRepository<MotIntraOperativeInstruction, Long> {

    Page<MotIntraOperativeInstruction> findByIoiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotIntraOperativeInstruction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotIntraOperativeInstruction> findByIoiNameContains(String key);

}
            
