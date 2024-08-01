package com.cellbeans.hspa.mstmlcchecklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstMlcChecklistRepository extends JpaRepository<MstMlcChecklist, Long> {

    Page<MstMlcChecklist> findByMcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstMlcChecklist> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstMlcChecklist> findByMcNameContains(String key);

}
            
