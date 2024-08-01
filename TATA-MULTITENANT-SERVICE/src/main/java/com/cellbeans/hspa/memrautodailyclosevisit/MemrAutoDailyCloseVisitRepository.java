package com.cellbeans.hspa.memrautodailyclosevisit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemrAutoDailyCloseVisitRepository extends JpaRepository<MemrAutoDailyCloseVisit, Long> {
    Page<MemrAutoDailyCloseVisit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MemrAutoDailyCloseVisit> findByAdcvUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalse(String unitname, String servicename, Pageable page);

}

