package com.cellbeans.hspa.trnemergencybedhistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnEmergencyBedHistoryRepository extends JpaRepository<TrnEmergencyBedHistory, Long> {
    //  Page<TrnEmergencyBedAllocation> findByRouteNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnEmergencyBedHistory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    // List<TrnEmergencyBedAllocation> findByRouteNameContains(String key);
}
            
