package com.cellbeans.hspa.mipdblock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdBlockRepository extends JpaRepository<MipdBlock, Long> {

    Page<MipdBlock> findByBlockNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdBlock> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdBlock> findByBlockNameContains(String key);

}
            
