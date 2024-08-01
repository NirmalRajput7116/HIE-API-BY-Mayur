package com.cellbeans.hspa.minvgrnitemchecklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvGrnItemChecklistRepository extends JpaRepository<MinvGrnItemChecklist, Long> {

    Page<MinvGrnItemChecklist> findByGicNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvGrnItemChecklist> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvGrnItemChecklist> findByGicNameContains(String key);

}
            
