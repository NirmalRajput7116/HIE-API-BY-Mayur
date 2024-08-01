package com.cellbeans.hspa.mstpreoperativeinstructions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPreOperativeInstructionsRepository extends JpaRepository<MstPreOperativeInstructions, Long> {

    Page<MstPreOperativeInstructions> findByPoiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPreOperativeInstructions> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstPreOperativeInstructions> findByPoiNameContains(String key);

}
            
