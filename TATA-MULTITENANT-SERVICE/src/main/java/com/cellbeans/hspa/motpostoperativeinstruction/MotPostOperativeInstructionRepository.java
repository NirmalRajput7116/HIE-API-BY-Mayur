package com.cellbeans.hspa.motpostoperativeinstruction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotPostOperativeInstructionRepository extends JpaRepository<MotPostOperativeInstruction, Long> {

    Page<MotPostOperativeInstruction> findByPoiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotPostOperativeInstruction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotPostOperativeInstruction> findByPoiNameContains(String key);

}
            
