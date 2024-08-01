package com.cellbeans.hspa.mstoperationresult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstOperationResultRepository extends JpaRepository<MstOperationResult, Long> {
    //Page<MstOperationResult> findByOrNameContainsAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
    Page<MstOperationResult> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    // List<MstOperationResult> findByOrNameContains(String key);
}
            
