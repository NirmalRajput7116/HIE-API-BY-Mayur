package com.cellbeans.hspa.macparent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacParentRepository extends JpaRepository<MacParent, Long> {

    Page<MacParent> findByParentNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacParent> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacParent> findByParentNameContains(String key);

}
            
