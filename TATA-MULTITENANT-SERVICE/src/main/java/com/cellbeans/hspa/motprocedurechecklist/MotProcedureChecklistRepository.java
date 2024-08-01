package com.cellbeans.hspa.motprocedurechecklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotProcedureChecklistRepository extends JpaRepository<MotProcedureChecklist, Long> {

    Page<MotProcedureChecklist> findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotProcedureChecklist> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotProcedureChecklist> findByPcNameContains(String key);

}
            
