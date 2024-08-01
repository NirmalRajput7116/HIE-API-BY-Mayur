package com.cellbeans.hspa.motpreoperativeinstruction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotPreOperativeInstructionRepository extends JpaRepository<MotPreOperativeInstruction, Long> {

    Page<MotPreOperativeInstruction> findByPoiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotPreOperativeInstruction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotPreOperativeInstruction> findByPoiNameContains(String key);

}
            
