package com.cellbeans.hspa.mstpostoperativeinstructions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPostOperativeInstructionsRepository extends JpaRepository<MstPostOperativeInstructions, Long> {

    Page<MstPostOperativeInstructions> findByPooiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstPostOperativeInstructions> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstPostOperativeInstructions> findByPooiNameContains(String key);

}
            
