package com.cellbeans.hspa.macaccounthead;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacAccountHeadRepository extends JpaRepository<MacAccountHead, Long> {

    Page<MacAccountHead> findByAhNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacAccountHead> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacAccountHead> findByAhNameContains(String key);

}
            
