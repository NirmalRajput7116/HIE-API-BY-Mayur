package com.cellbeans.hspa.motoperationresult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotOperationResultRepository extends JpaRepository<MotOperationResult, Long> {

    Page<MotOperationResult> findByOprNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MotOperationResult> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MotOperationResult> findByOprNameContains(String key);

}
