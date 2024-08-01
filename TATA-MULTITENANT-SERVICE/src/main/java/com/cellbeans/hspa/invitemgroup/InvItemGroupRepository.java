package com.cellbeans.hspa.invitemgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemGroupRepository extends JpaRepository<InvItemGroup, Long> {

    Page<InvItemGroup> findByIgNameContainsAndIsActiveTrueAndIsDeletedFalseOrIgLedgerNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIgName(String name, String name1, Pageable page);

    Page<InvItemGroup> findAllByIsActiveTrueAndIsDeletedFalseOrderByIgName(Pageable page);

    List<InvItemGroup> findByIgNameContains(String key);

}
            
