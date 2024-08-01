package com.cellbeans.hspa.mstdischargesummaryform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstDischargeSummaryFormRepository extends JpaRepository<MstDischargeSummaryForm, Long> {

    Page<MstDischargeSummaryForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstDischargeSummaryForm> findByDsfNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

}
