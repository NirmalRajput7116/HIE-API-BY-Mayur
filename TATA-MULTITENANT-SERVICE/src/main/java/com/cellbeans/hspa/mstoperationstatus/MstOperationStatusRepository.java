package com.cellbeans.hspa.mstoperationstatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstOperationStatusRepository extends JpaRepository<MstOperationStatus, Long> {

    Page<MstOperationStatus> findByOsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstOperationStatus> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstOperationStatus> findByOsNameContains(String key);

}
            
