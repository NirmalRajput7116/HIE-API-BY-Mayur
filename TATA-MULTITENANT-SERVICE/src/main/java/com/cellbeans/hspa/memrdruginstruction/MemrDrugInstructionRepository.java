package com.cellbeans.hspa.memrdruginstruction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrDrugInstructionRepository extends JpaRepository<MemrDrugInstruction, Long> {

    Page<MemrDrugInstruction> findByDiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrDrugInstruction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrDrugInstruction> findByDiNameContains(String key);

}
            
