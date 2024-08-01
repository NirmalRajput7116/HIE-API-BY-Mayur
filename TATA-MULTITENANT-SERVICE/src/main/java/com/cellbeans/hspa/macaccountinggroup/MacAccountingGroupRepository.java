package com.cellbeans.hspa.macaccountinggroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacAccountingGroupRepository extends JpaRepository<MacAccountingGroup, Long> {

    Page<MacAccountingGroup> findByAgNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacAccountingGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacAccountingGroup> findByAgNameContains(String key);

}
            
