package com.cellbeans.hspa.trnundermaintenancebed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnUndermaintenanceBedRepository extends JpaRepository<TrnUndermaintenanceBed, Long> {

    Page<TrnUndermaintenanceBed> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}
            
